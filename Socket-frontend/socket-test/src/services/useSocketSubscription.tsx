import { useEffect, useRef, useCallback } from 'react';
import { useSocketProvider } from './SocketProvider';


interface UseSocketSubscriptionParams {
  topic: string;
  onMessage: (message: any) => void;
}

interface UseSocketSubscriptionResult {
  isSocketConnected: boolean;
}

const useSocketSubscription = ({
  topic,
  onMessage,
}: UseSocketSubscriptionParams): UseSocketSubscriptionResult => {
  const { isSocketConnected, socket } = useSocketProvider();
  const subscriptionRef = useRef<any>(null);
  console.log({isSocketConnected});
  
  const subscribe = useCallback(() => {
    if (!socket?.current || !isSocketConnected) return;

    try {
      subscriptionRef.current = socket.current.subscribe(topic, (message: any) => {
        onMessage(message);
      });
    } catch (error) {
      console.error(`Failed to subscribe to topic "${topic}":`, error);
    }
  }, [isSocketConnected, socket, topic]);

  useEffect(() => {
    if (isSocketConnected && topic) {
      subscribe();
    }

    return () => {
      if (subscriptionRef.current) {
        try {
          subscriptionRef.current.unsubscribe();
        } catch (error) {
          console.error(`Failed to unsubscribe from topic "${topic}":`, error);
        }
      }
    };
  }, [isSocketConnected, topic, subscribe]);

  return { isSocketConnected };
};

export default useSocketSubscription;

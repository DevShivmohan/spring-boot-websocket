import React, { createContext, useContext, useState, useRef, useEffect } from 'react';
import { Client } from '@stomp/stompjs';

const settings = {
    SOCKET_URL: 'ws://localhost:8080/chat/websocket/websocket'
}

interface SocketContextProps {
  isSocketConnected: boolean;
  socket: React.MutableRefObject<Client | null>;
  setIsSocketConnected: React.Dispatch<React.SetStateAction<boolean>>;
}

const SocketProviderContext = createContext<SocketContextProps | undefined>(undefined);

export const SocketProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [isSocketConnected, setIsSocketConnected] = useState(false);
  const socket = useRef<Client | null>(null);

  // Replace `getToken` and `url` with your actual implementations
  const getToken = () => 'your-auth-token';
  const url = `${
    settings.SOCKET_URL
  }`;

  useEffect(() => {
    if (!isSocketConnected) {
      socket.current = new Client({
        connectHeaders: {
          Authorization: `Bearer ${getToken()}`,
        },
        // brokerURL: `${url}&X-Request-Id=${uuidv4()}&TabId=${window.name}`,
        brokerURL: url,
        debug: (str: string) => {
          console.debug(`STOMP debug: ${str}`);
        },
        onConnect: () => {
          setIsSocketConnected(true);
        },
        onDisconnect: () => {
          setIsSocketConnected(false);
        },
        onStompError: (error : any) => {
          console.error("STOMP connection error:", error);
        },
      });

      socket.current.activate();
    }

    return () => {
      socket.current?.deactivate();
    };
  }, []);
  
  return (
    <SocketProviderContext.Provider value={{ isSocketConnected, socket, setIsSocketConnected }}>
      {children}
    </SocketProviderContext.Provider>
  );
};

// Hook to use the context
export const useSocketProvider = (): SocketContextProps => {
  const context = useContext(SocketProviderContext);
  if (!context) {
    throw new Error('useSocketProvider must be used within a SocketProvider');
  }
  return context;
};

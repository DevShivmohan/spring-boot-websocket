import './App.css'
import useSocketSubscription from './services/useSocketSubscription'

function App() {
  
  const onSocketHandler = (temp : any): void => {
    // Parse the incoming socket data
    const data = JSON.parse(temp.body);
    console.log({data})
  }

  useSocketSubscription({
    topic: "/topic/user",
    onMessage: onSocketHandler
  })

  return (

    <>
      <div>
        <h1>Let's connect with your socket</h1>
      </div>
    </>
  )
}

export default App

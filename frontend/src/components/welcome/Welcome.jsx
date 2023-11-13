import { useSelector } from "react-redux";
import "./welcomeStyle.css";

const Welcome = () => {
  const { user } = useSelector((state) => state.auth);
  return (
    <div className="welcome-container">
      <h1 className="title">Dashboard</h1>
      <h2 className="subtitle">
        Welcome Back <strong>{user && user.name}</strong>
      </h2>
    </div>
  );
};

export default Welcome;

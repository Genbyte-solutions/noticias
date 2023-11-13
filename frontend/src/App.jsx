import {
  BrowserRouter,
  Route,
  Routes as ReactDomRoutes
} from "react-router-dom";
import "./App.css";
import AdminRoutes from "./routes/Admin.Routes";
import MainRoutes from "./routes/Routes";

function App() {
  return (
    <BrowserRouter>
      <ReactDomRoutes>
        <Route path="/*" element={<MainRoutes />} />
        <Route path="/administrador/*" element={<AdminRoutes />} />
      </ReactDomRoutes>
    </BrowserRouter>
  );
}

export default App;

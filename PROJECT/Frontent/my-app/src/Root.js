import { Outlet } from "react-router-dom";
import Footer from "./components/footer";
import Header from "./components/header";
function RootLayout(){
    return(<>
  <Header></Header>
    <Outlet></Outlet>
    <Footer></Footer>
    </>)
    
}
export default RootLayout;
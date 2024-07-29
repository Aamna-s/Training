import React, { useEffect } from "react";
import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";
import RootLayout from "./../Root";
import Header from "./header";
import Footer from "./footer";

function Protected({ Component }) {
  const navigate = useNavigate();

  useEffect(() => {
    if (!Cookies.get('token')) {
      navigate('/');
    }
  }, [navigate]);

  return (
       <> 
      <Component />
      </>
   
  );
}

export default Protected;

import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import RootLayout from './Root';
import CreateAccount from './pages/CreateAccount';
import Login from './pages/Login';
import Transaction from './pages/Transaction';
import AllUsers from './pages/AllUsers';
import UserHistory from './pages/UserHistory';
import EditAccount from './pages/EditAccount';
import User from './pages/User';
import MyProfile from './pages/MyProfile';
import AllTransactions from './pages/AllTransactions';
import Protected from './components/Protected';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      { path: '/home', element: <Protected Component={Home} /> },
      { path: '/createAccount', element: <Protected Component={CreateAccount} /> },
      { path: '/', element: <Login /> },
      { path: '/transaction', element: <Protected Component={Transaction} /> },
      { path: '/userHistory', element: <Protected Component={UserHistory} /> },
      { path: '/allUsers', element: <Protected Component={AllUsers} /> },
      { path: '/editAccount/:id', element: <Protected Component={EditAccount} /> },
      { path: '/userDashboard', element: <Protected Component={User} /> },
      { path: '/viewAccount', element: <Protected Component={MyProfile} /> },
      { path: '/viewTransactions', element: <Protected Component={AllTransactions} /> },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;

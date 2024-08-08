import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';

function MyProfile() {
    const [account, setAccount] = useState(null);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const Logout = () => {
        Cookies.remove('token'); // Specify the cookie name to remove
        navigate('/');
    };

    useEffect(() => {
        const fetchAccount = async () => {
            try {
                const token = Cookies.get('token');
                 const accountId = JSON.parse(Cookies.get('account') || '{}').accountId;
                
                const response = await axios.get(`http://localhost:8080/api/v1/accounts/Id/${accountId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                }
            );
            setAccount(response.data);
            console.log(response.data)  
                
            } catch (err) {
                if (err.response && err.response.status === 401) {
                    // Handle unauthorized access
                    setError("Session expired. Please log in again.");
                } else {
                    setError("Error fetching account details");
                }
                console.error(err);
            }
        };
        fetchAccount();
    }, []);

    if (error) {
        return <div>{error}</div>;
    }

    if (!account) {
        return <div>Loading...</div>;
    }

    return (
        <>
            <div className="content_info">
                <nav id="menu">
                    <div className="navbar yamm navbar-default">
                        <div className="container">
                            <div className="row">
                                <div className="navbar-header">
                                    <button
                                        type="button"
                                        data-toggle="collapse"
                                        data-target="#navbar-collapse-1"
                                        className="navbar-toggle"
                                    >
                                        <span className="icon-bar"></span>
                                        <span className="icon-bar"></span>
                                        <span className="icon-bar"></span>
                                    </button>
                                </div>
                                <div id="navbar-collapse-1" className="navbar-collapse collapse">
                                    <ul className="nav navbar-nav">
                                        <li className="dropdown">
                                            <Link to="/userDashboard" className="dropdown-toggle">
                                                Home
                                            </Link>
                                        </li>
                                        <li className="dropdown">
                                            <Link to="/transaction" className="dropdown-toggle">
                                                Transaction
                                            </Link>
                                        </li>
                                        <li className="dropdown">
                                            <Link to="/userHistory" className="dropdown-toggle">
                                                View History
                                            </Link>
                                        </li>
                                        <li className="dropdown">
                                            <Link to="/viewAccount" className="dropdown-toggle">
                                                View Account
                                            </Link>
                                        </li>
                                        <li className="dropdown" onClick={Logout}>
                                            <Link to="" className="dropdown-toggle">
                                                Logout
                                            </Link>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </nav>
                <div className="paddings">
                    <div className="container">
                        <div className="row user-area">
                            <div className="col-md-8">
                                <ul className="nav nav-tabs" role="tablist">
                                    <li role="presentation">
                                        <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">View Account</a>
                                    </li>
                                </ul>
                                <div className="tab-content">
                                    <div role="tabpanel" className="tab-pane fade in active" id="tab1">
                                        <h3>Account Details</h3>
                                        <p><strong>Account ID:</strong> {account.accountId}</p>
                                        <p><strong>Full Name:</strong> {account.name}</p>
                                        <p><strong>Username:</strong> {account.username}</p>
                                        <p><strong>Email:</strong> {account.useremail}</p>
                                        <p><strong>Address:</strong> {account.address}</p>
                                        <p><strong>Bank Balance:</strong> {account.bankBalance}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default MyProfile;

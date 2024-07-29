import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

function Transaction() {
    const navigate = useNavigate();
    const [amount, setAmount] = useState("");
    const [toAccount, setToAccount] = useState("");
    const [error, setError] = useState(""); // State for error messages
    const token = Cookies.get('token');
    const account = JSON.parse(Cookies.get('account') || '{}');
    const accountId = account.accountId;


    const Logout = () => {
        Cookies.remove('token'); // Specify the cookie name to remove
        navigate('/');
    };
    const handleAmountChange = (event) => {
        setAmount(event.target.value);
    };

    const handleToAccountChange = (event) => {
        setToAccount(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        setError("");

        axios.get(`http://localhost:8080/api/v1/accounts/${toAccount}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            const account = response.data;
            return axios.post("http://localhost:8080/api/v1/transactions", {
                toFromAccountId: toAccount,
                amount: amount,
                accountId: accountId
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
        })
        .then(response => {
            if (response.status === 200) {
                console.log(response.data);
                navigate(`/userHistory`);
            }
        })
        .catch(error => {
            if (error.response) {
                if (error.response.status === 400) {
                    // Show error message on screen if status code is 400
                    const errorMessage = error.response.data || 'Bad Request. Please check your input.';
                    setError(errorMessage);
                } else {
                    // Handle other HTTP errors
                    setError('An error occurred. Please try again later.');
                }
            } else {
                // Handle unexpected errors
                setError('An unexpected error occurred.');
            }
            console.error('Error:', error);
        });
    };

    return (
        <>
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
                                            User History
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
                                    <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">Transaction</a>
                                </li>
                            </ul>
                            <div className="tab-content">
                                <div role="tabpanel" className="tab-pane fade in active" id="tab1">
                                    <form action="#" className="form-theme" onSubmit={handleSubmit}>
                                        <label>Account Number</label>
                                        <input
                                            type="text"
                                            value={accountId} // Pre-fill with account ID
                                            name="fromAccount"
                                            className="input"
                                            disabled // Make the field read-only
                                        />

                                        <label>Amount</label>
                                        <input
                                            type="number"
                                            value={amount}
                                            name="amount"
                                            placeholder=""
                                            className="input"
                                            onChange={handleAmountChange}
                                        />

                                        <label>To Account</label>
                                        <input
                                            type="text"
                                            value={toAccount}
                                            name="toAccount"
                                            placeholder=""
                                            className="input"
                                            onChange={handleToAccountChange}
                                        />

                                        {error && (
                                            <div className="error-message" style={{ color: 'red', marginTop: '10px' }}>
                                                {error}
                                            </div>
                                        )}

                                        <input type="submit" className="btn" value="Save Changes" />
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Transaction;

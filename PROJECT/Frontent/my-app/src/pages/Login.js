import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

function Login() {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [usernameError, setUsernameError] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [generalError, setGeneralError] = useState("");

    const usernameChangeHandler = (event) => {
        setUsername(event.target.value);
        setUsernameError(""); // Clear username error when user types
    };

    const passwordChangeHandler = (event) => {
        setPassword(event.target.value);
        setPasswordError(""); // Clear password error when user types
    };

    const submitHandler = (event) => {
        event.preventDefault(); // Prevent form from submitting the traditional way

        // Reset errors
        setUsernameError("");
        setPasswordError("");
        setGeneralError("");

        axios.post("http://localhost:8080/api/v1/accounts/login", {
            username: username,
            password: password
        })
        .then((res) => {
            // Set the token and account information in cookies
            Cookies.set('token', res.data.token, { expires: 1 });
            Cookies.set('account', JSON.stringify(res.data.account));

            const account = res.data.account;
            // Navigate based on user role
            if (account.roles.includes("admin")) {
                navigate(`/home`);
            } else {
                navigate(`/userDashboard`);
            }
        })
        .catch((error) => {
            console.error('Login error:', error);

            // Handle errors
            if (error.response) {
                // Backend error
                const errorMessage = 'Password or Username is incorrect';
                
                // Display error message under username or password fields
                if (errorMessage.includes("username")) {
                    setUsernameError(errorMessage);
                } else if (errorMessage.includes("password")) {
                    setPasswordError(errorMessage);
                } else {
                    setGeneralError(errorMessage);
                }
            } else if (error.request) {
                // No response received
                setGeneralError('No response received from the server');
            } else {
                // Error setting up the request
                setGeneralError('Error in setting up the request');
            }
        });
    };

    const goBack = () => {
        navigate(-1); // Navigate to the previous page
    };

    return (
        <>
            <div className="content_info">
                <div className="paddings">
                    <div className="container">
                        <div className="row user-area">
                            <div className="col-md-8">
                                
                                <ul className="nav nav-tabs" role="tablist">
                                    <li role="presentation">
                                        <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">Login Account</a>
                                    </li>
                                    <li role="presentation">
                                        <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab" onClick={goBack}>Back</a>
                                    </li>
                                </ul>
                                <div className="tab-content">
                                    <div role="tabpanel" className="tab-pane fade in active" id="tab1">
                                        <form className="form-theme" onSubmit={submitHandler}>
                                            <label>Username</label>
                                            <input
                                                type="text"
                                                value={username}
                                                name="username"
                                                placeholder="Federick"
                                                className="input"
                                                onChange={usernameChangeHandler}
                                            />
                                            {usernameError && (
                                                <div className="error-message" style={{ color: 'red', marginTop: '5px' }}>
                                                    {usernameError}
                                                </div>
                                            )}

                                            <label>Password</label>
                                            <input
                                                type="password"
                                                value={password}
                                                name="password"
                                                placeholder=""
                                                className="input"
                                                onChange={passwordChangeHandler}
                                            />
                                            {passwordError && (
                                                <div className="error-message" style={{ color: 'red', marginTop: '5px' }}>
                                                    {passwordError}
                                                </div>
                                            )}

                                            {generalError && (
                                                <div className="error-message" style={{ color: 'red', marginTop: '10px' }}>
                                                    {generalError}
                                                </div>
                                            )}

                                            <input type="submit" className="btn" value="Login" />
                                        </form>
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

export default Login;

import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import { FaEye, FaEyeSlash } from 'react-icons/fa'; // Import eye icons

function Login() {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [usernameError, setUsernameError] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [generalError, setGeneralError] = useState("");
    const [passwordVisible, setPasswordVisible] = useState(false); // Default to false
    const [isLoading, setIsLoading] = useState(false); // Loading state

    const usernameChangeHandler = (event) => {
        setUsername(event.target.value);
        setUsernameError(""); 
    };

    const passwordChangeHandler = (event) => {
        setPassword(event.target.value);
        setPasswordError(""); 
    };

    const togglePasswordVisibility = () => {
        setPasswordVisible(!passwordVisible);
    };

    const submitHandler = (event) => {
        event.preventDefault(); 
        setIsLoading(true); // Set loading state

        setUsernameError("");
        setPasswordError("");
        setGeneralError("");

        axios.post("http://localhost:8080/api/v1/accounts/login", {
            username: username,
            password: password
        })
        .then((res) => {
            Cookies.set('token', res.data.token);
            Cookies.set('account', JSON.stringify(res.data.account));

            const account = res.data.account;
            
            if (account?.roles?.includes("admin")) {
                navigate(`/home`);
            } else {
                navigate(`/userDashboard`);
            }
        })
        .catch((error) => {
            console.error('Login error:', error.response);
    
            if (error.response) {
                const { status, data } = error.response;
    
                if (status === 401) {
                    setGeneralError(data.message || 'Unauthorized access');
                } else if (status === 500) {
                    if (data?.message?.includes("username")) {
                        setUsernameError(data.message);
                    } else if (data?.message?.includes("password")) {
                        setPasswordError(data.message);
                    } else {
                        setGeneralError('Bad Credentials');
                    }
                } else {
                    setGeneralError(data.message || 'An error occurred. Please try again.');
                }
            } else if (error.request) {
                setGeneralError('No response received from the server');
            } else {
                setGeneralError('Error in setting up the request');
            }
        })
        .finally(() => {
            setIsLoading(false); // Reset loading state
        });
    };

    const goBack = () => {
        navigate(-1); 
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
                                            <div className="password-wrapper">
                                                <input
                                                    type={passwordVisible ? "text" : "password"}
                                                    value={password}
                                                    name="password"
                                                    placeholder=""
                                                    className="input password-input"
                                                    onChange={passwordChangeHandler}
                                                />
                                                <button
                                                    type="button"
                                                    className="password-toggle-btn"
                                                    onClick={togglePasswordVisibility}
                                                    aria-label="Toggle password visibility"
                                                >
                                                    {passwordVisible ? <FaEye /> : <FaEyeSlash />} {/* Toggle icon */}
                                                </button>
                                            </div>
                                            
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

                                            <input type="submit" className="btn" value={isLoading ? "Logging in..." : "Login"} disabled={isLoading} />
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

import React, { useState, useEffect } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';

function EditAccount() {
    const { id } = useParams();
    const navigate = useNavigate(); 
    
    const [userName, setUserName] = useState("");
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [password, setPassword] = useState("");
    const [name, setName] = useState("");

    useEffect(() => {
        const token = Cookies.get('token');
        axios.get(`http://localhost:8080/api/v1/accounts/${id}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then((response) => {
            const account = response.data;
            setName(account.name);
            setUserName(account.username);
            setEmail(account.useremail);
            setAddress(account.address);
        })
        .catch((error) => {
            console.error('Error fetching account details:', error);
        });
    }, [id]);

    const handleChange = (setter) => (event) => {
        setter(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const token = Cookies.get('token');
        axios.patch(`http://localhost:8080/api/v1/accounts`, {
            accountId:id,
            name,
            username: userName,
            password,
            useremail: email,
            address
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
        .then((response) => {
            console.log("Account updated successfully:", response.data);
            navigate('/allUsers');
        })
        .catch((error) => {
            console.error('Error updating account:', error);
        });
    };

    const handleLogout = () => {
        Cookies.remove('token');
        navigate('/');
    };

    return (
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
                                        <Link to="/home" className="dropdown-toggle">
                                            Home
                                        </Link>
                                    </li>
                                    <li className="dropdown">
                                        <Link to="/allUsers" className="dropdown-toggle">
                                            All Users
                                        </Link>
                                    </li>
                                    <li className="dropdown">
                                        <Link to="/viewTransactions" className="dropdown-toggle">
                                            All Transactions
                                        </Link>
                                    </li>
                                    <li className="dropdown" onClick={handleLogout}>
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
                                    <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">Edit Account</a>
                                </li>
                            </ul>
                            <div className="tab-content">
                                <div role="tabpanel" className="tab-pane fade in active" id="tab1">
                                    <form action="#" className="form-theme" onSubmit={handleSubmit}>
                                        <label>Account number</label>
                                        <input
                                            type="number"
                                            value={id}
                                            name="accountId"
                                            className="input"
                                            disabled
                                        />
                                        <label>Full Name</label>
                                        <input
                                            type="text"
                                            value={name}
                                            name="name"
                                            className="input"
                                            onChange={handleChange(setName)}
                                        />
                                        <label>Username</label>
                                        <input
                                            type="text"
                                            value={userName}
                                            name="userName"
                                            placeholder="Federick"
                                            className="input"
                                            onChange={handleChange(setUserName)}
                                        />
                                        <label>Email</label>
                                        <input
                                            type="email"
                                            value={email}
                                            name="email"
                                            placeholder="abc@gmail.com"
                                            className="input"
                                            onChange={handleChange(setEmail)}
                                        />
                                        <label>Address</label>
                                        <input
                                            type="text"
                                            value={address}
                                            name="address"
                                            placeholder=""
                                            className="input"
                                            onChange={handleChange(setAddress)}
                                        />
                                        <label>Password</label>
                                        <input
                                            type="password"
                                            value={password}
                                            name="password"
                                            placeholder=""
                                            className="input"
                                            onChange={handleChange(setPassword)}
                                        />
                                        <input type="submit" className="btn" value="Save Changes" />
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EditAccount;

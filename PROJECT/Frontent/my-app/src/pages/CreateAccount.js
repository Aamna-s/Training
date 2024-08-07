import axios from 'axios';
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

function CreateAccount() {
  const [name, setName] = useState("");
  const [role, setRole] = useState("");
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [address, setAddress] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(""); // Initialize error state

  const nameChangeHandler = (event) => setName(event.target.value);
  const roleChangeHandler = (event) => setRole(event.target.value);
  const emailChangeHandler = (event) => setEmail(event.target.value);
  const addressChangeHandler = (event) => setAddress(event.target.value);
  const passwordChangeHandler = (event) => { 
    setError('');
    setPassword(event.target.value);}

  const token = Cookies.get('token');
  const navigate = useNavigate();

  const Logout = () => {
    Cookies.remove('token'); // Specify the cookie name to remove
    navigate('/');
  };

  const usernameChangeHandler = async (event) => {
    const usernameInput = event.target.value;
    setError('')
    setUsername(usernameInput);

    try {
      const response = await axios.get(`http://localhost:8080/api/v1/accounts/${usernameInput}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.data != null) {
        setError("Username already exists");
      } else {
        setError("");
      }
    } catch (error) {
      if (error.response && error.response.status !== 404) {
        setError("An error occurred while checking username.");
      } else {
        setError("");
      }
    }
  };

  // Password validation function
  const validatePassword = (password) => {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{6,}$/;
    return passwordRegex.test(password);
  };

  const submitHandler = (event) => {
    event.preventDefault();

    if (!username) {
      setError("Username cannot be null");
      return;
    }
    if (!password) {
      setError("Password cannot be null");
      return;
    }
    if (!validatePassword(password)) {
      setError("Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 6 characters long.");
      return;
    }
    if (!role) {
      setError("Role cannot be null");
      return;
    }
    if (!name) {
      setError("Name cannot be null");
      return;
    }

    axios.post("http://localhost:8080/api/v1/accounts", {
      name,
      username,
      password,
      useremail: email,
      address,
      roles: role
    }, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
    .then(response => {
      if (response.status === 200) {
        navigate(`/allUsers`);
      }
    })
    .catch(error => {
      if (!error && error.response.status === 403) {
        setError('An unexpected error occurred.');
      }
    });
  };

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
                    <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">Create Account</a>
                  </li>
                </ul>
                <div className="tab-content">
                  <div role="tabpanel" className="tab-pane fade in active" id="tab1">
                    <form action="#" className="form-theme" onSubmit={submitHandler}>
                      <label>Full Name</label>
                      <input
                        type="text"
                        value={name}
                        name="name"
                        placeholder="Federick"
                        className="input"
                        onChange={nameChangeHandler}
                      />
                      <label>Username</label>
                      <input
                        type="text"
                        value={username}
                        name="username"
                        placeholder="Federick"
                        className="input"
                        onChange={usernameChangeHandler}
                      />
                      <label>Email</label>
                      <input
                        type="email"
                        value={email}
                        name="email"
                        placeholder="abc@gmail.com"
                        className="input"
                        onChange={emailChangeHandler}
                      />
                      <label>Address</label>
                      <input
                        type="text"
                        value={address}
                        name="address"
                        placeholder=""
                        className="input"
                        onChange={addressChangeHandler}
                      />
                      <label>Password</label>
                      <input
                        type="password"
                        value={password}
                        name="password"
                        placeholder=""
                        className="input"
                        onChange={passwordChangeHandler}
                      />
                      <label>Role</label>
                      <input
                        type="text"
                        value={role}
                        name="role"
                        placeholder="Federick"
                        className="input"
                        onChange={roleChangeHandler}
                      />
                      <input type="submit" className="btn" value="Save Changes" />
                      {error && (
                        <div className="error-message" style={{ color: 'red', marginTop: '10px' }}>
                          {error}
                        </div>
                      )}
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

export default CreateAccount;

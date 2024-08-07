import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, IconButton, Tooltip, CircularProgress } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { useNavigate, Link } from 'react-router-dom';
import Cookies from 'js-cookie';

function AllUsers() {
    const [accounts, setAccounts] = useState([]);
    const [loading, setLoading] = useState(true); // Add loading state
    const navigate = useNavigate();
    const token = Cookies.get('token');
    
    const Logout = () => {
        Cookies.remove('token'); // Specify the cookie name to remove
        navigate('/');
    };

    useEffect(() => {
        if (token) {
            axios.get('http://localhost:8080/api/v1/accounts', {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
            .then(response => {
                console.log('Accounts fetched:', response.data);
                setAccounts(response.data);
                setLoading(false); // Data loaded, set loading to false
            })
            .catch(error => {
                console.error('Error fetching Accounts:', error);
                setLoading(false); // Error occurred, set loading to false
            });
        }
    }, []);

    const handleAddUser = () => {
        navigate(`/createAccount`);
    };

    const handleEditUser = (accountId) => {
        navigate(`/editAccount/${accountId}`);
    };

    const handleDeleteUser = (accountId) => {
        axios.delete(`http://localhost:8080/api/v1/accounts/${accountId}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            console.log('User deleted:', response.data);
            setAccounts(accounts.filter(account => account.accountId !== accountId));
        })
        .catch(error => {
            console.error('Error deleting user:', error);
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
            <TableContainer component={Paper}>
                {loading ? (
                    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
                        <CircularProgress />
                    </div>
                ) : (
                    <Table sx={{ minWidth: 650 }} aria-label="Users table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Account Number</TableCell>
                                <TableCell>Full Name</TableCell>
                                <TableCell>User Name</TableCell>
                                <TableCell>Password</TableCell>
                                <TableCell>Email</TableCell>
                                <TableCell>Bank Balance</TableCell>
                                <TableCell>Address</TableCell>
                                <TableCell>Roles</TableCell>
                                <TableCell>Actions</TableCell>
                                <TableCell>
                                    <Button variant="contained" color="primary" startIcon={<AddIcon />} onClick={handleAddUser}>
                                        Add User
                                    </Button>
                                </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {accounts.length > 0 ? (
                                accounts.map((account) => (
                                    <TableRow key={account.accountId || ''}>
                                        <TableCell>{account.accountId|| ''}</TableCell>
                                        <TableCell>{account.name|| ''}</TableCell>
                                        <TableCell>{account.username|| ''}</TableCell>
                                        <TableCell>{account.password|| ''}</TableCell>
                                        <TableCell>{account.useremail|| ''}</TableCell>
                                        <TableCell>{account.bankBalance|| ''}</TableCell>
                                        <TableCell>{account.address|| ''}</TableCell>
                                        <TableCell>{account.roles|| ''}</TableCell>
                                        <TableCell>
                                            <Tooltip title="Edit User">
                                                <IconButton color="warning" onClick={() => handleEditUser(account.accountId)}>
                                                    <EditIcon />
                                                </IconButton>
                                            </Tooltip>
                                            <Tooltip title="Delete User">
                                                <IconButton color="error" onClick={() => handleDeleteUser(account.accountId)}>
                                                    <DeleteIcon />
                                                </IconButton>
                                            </Tooltip>
                                        </TableCell>
                                    </TableRow>
                                ))
                            ) : (
                                <TableRow>
                                    <TableCell colSpan={9} align="center">No Accounts found</TableCell>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                )}
            </TableContainer>
        </>
    );
}

export default AllUsers;

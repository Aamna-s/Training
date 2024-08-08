import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import { Link,useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

function UserHistory() {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const token = Cookies.get('token');
    const id = JSON.parse(Cookies.get('account') || '{}').accountId;
    const navigate = useNavigate();

    const Logout = () => {
        Cookies.remove('token'); // Specify the cookie name to remove
        navigate('/');
    };
    useEffect(() => {
        setLoading(true); // Set loading to true when starting to fetch data
        axios.get(`http://localhost:8080/api/v1/transactions/Id/${id}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            console.log('Transactions fetched:', response.data);
            setTransactions(response.data);
            setLoading(false);
        })
        .catch(error => {
            console.error('Error fetching transactions:', error);
            setError('Error fetching transactions');
            setLoading(false);
        });
    }, [id, token]); // Added id and token as dependencies

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
                                    className="navbar-toggle">
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
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="transactions table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Transaction ID</TableCell>
                            <TableCell>Account ID</TableCell>
                            <TableCell>To/From Account ID</TableCell>
                            <TableCell>Date</TableCell>
                            <TableCell>Amount</TableCell>
                            <TableCell>Debit/Credit</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {loading ? (
                            <TableRow>
                                <TableCell colSpan={6} align="center">Loading...</TableCell>
                            </TableRow>
                        ) : error ? (
                            <TableRow>
                                <TableCell colSpan={6} align="center">{error}</TableCell>
                            </TableRow>
                        ) : transactions.length > 0 ? (
                            transactions.map((transaction) => (
                                <TableRow key={transaction.transactionId}>
                                    <TableCell>{transaction.transactionId}</TableCell>
                                    <TableCell>{transaction.accountId}</TableCell>
                                    <TableCell>{transaction.toFromAccountId}</TableCell>
                                    <TableCell>{transaction.date}</TableCell>
                                    <TableCell>{transaction.amount}</TableCell>
                                    <TableCell>{transaction.dbCrIndicator}</TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={6} align="center">No transactions found</TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        </>
    );
}

export default UserHistory;

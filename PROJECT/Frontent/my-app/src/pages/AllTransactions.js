import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, CircularProgress, TextField } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import { debounce } from 'lodash'; 

function AllTransactions() {
    const [transactions, setTransactions] = useState([]); // State for all transactions
    const [loading, setLoading] = useState(true); // State for loading status
    const [error, setError] = useState(""); // State for error messages
    const [searchTerm, setSearchTerm] = useState(""); // State for search term
    const token = Cookies.get('token');
    const navigate = useNavigate();

    const Logout = () => {
        Cookies.remove('token'); 
        navigate('/');
    };

    useEffect(() => {
        if (token) {
            axios.get('http://localhost:8080/api/v1/transactions', {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
            .then(response => {
                console.log('Transactions fetched:', response.data);
                setTransactions(response.data);
                setLoading(false); // Update loading status
            })
            .catch(error => {
                console.error('Error fetching transactions:', error);
                setError('Failed to load transactions.');
                setLoading(false); // Update loading status
            });
        } else {
            setError('No token found. Please log in.');
            setLoading(false); // Update loading status
        }
    }, [token]);

    const fetchFilteredTransactions =(searchValue) => {
        console.log(token)
        if (searchValue !== '') {
            axios.get(`http://localhost:8080/api/v1/transactions/get/${searchValue}`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(res => {
                    
                    setTransactions(res.data);
                    setLoading(false);
                })
                .catch(error => {
                    console.error('Error fetching filtered transactions:', error);
                    setError('Failed to filter transactions.');
                    setLoading(false);
                });
        } else {
          
            axios.get('http://localhost:8080/api/v1/transactions', {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
            .then(response => {
               
                setTransactions(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching transactions:', error);
                setError('Failed to load transactions.');
                setLoading(false);
            });
        }
    }; // Debounce delay of 300ms

    const handleSearch = (event) => {
        const value = event.target.value;
        setSearchTerm(value);

        fetchFilteredTransactions(parseInt(value));
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
                    <div className="navbar-content">
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
                        <TextField
                            variant="outlined"
                            label="Search"
                            type="number" // Ensure numeric input
                            value={searchTerm}
                            onChange={handleSearch}
                            style={{ 
                                margin: '16px', 
                                backgroundColor: 'white' ,
                                borderRadius: '8px', // Rounded corners
                                boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)'// Set background color to white
                            }}
                        />
                    </div>
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
                                <TableCell colSpan={6} align="center">
                                    <CircularProgress /> Loading...
                                </TableCell>
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

export default AllTransactions;

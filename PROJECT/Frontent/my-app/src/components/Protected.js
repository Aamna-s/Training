import React, { useEffect, useState } from 'react';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';

const styles = {
  container: {
    textAlign: 'center',
    marginTop: '50px',
  },
  message: {
    marginBottom: '20px',
    fontSize: '18px',
    color: '#333',
  },
  button: {
    padding: '10px 20px',
    fontSize: '16px',
    color: '#fff',
    backgroundColor: '#007bff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
  },
  loading: {
    textAlign: 'center',
    marginTop: '50px',
    fontSize: '18px',
    color: '#007bff',
  },
};

function Protected({ Component, allowedRoles }) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [hasRole, setHasRole] = useState(false);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const token = Cookies.get('token');
    const account = Cookies.get('account') ? JSON.parse(Cookies.get('account')) : null;
    const role = account ? account.roles : [];

    if (token) {
      setIsAuthenticated(true);
      if (allowedRoles.some(r => role.includes(r))) {
        setHasRole(true);
      }
    }

    setLoading(false);
  }, [allowedRoles]);

  const goBack = () => {
    navigate(-1); // Navigate to the previous page
  };

  if (loading) {
    return <div style={styles.loading}>Loading...</div>;
  }

  if (!isAuthenticated) {
    return (
      <div style={styles.container}>
        <p style={styles.message}>Please log in to access this page.</p>
        <button style={styles.button} onClick={goBack}>Go Back</button>
      </div>
    );
  }

  if (!hasRole) {
    return (
      <div style={styles.container}>
        <p style={styles.message}>You do not have permission to access this page.</p>
        <button style={styles.button} onClick={goBack}>Go Back</button>
      </div>
    );
  }

  return <Component />;
}

export default Protected;

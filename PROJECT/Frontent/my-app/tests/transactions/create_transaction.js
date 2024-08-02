Feature('Transaction Creation');

Scenario('Create a new transaction successfully', async ({ I }) => {
    // Open the login page and log in
    I.amOnPage('http://localhost:3000/');
    I.fillField('username', 'user'); // Replace with your username
    I.fillField('password', 'abc'); // Replace with your password
    I.click('Login');

    // Ensure login was successful
    I.see('Transaction');

    // Navigate to the Transaction page
    I.click('Transaction');
    I.see('Transaction');

    // Fill out the transaction form
    I.fillField('amount', '10'); // Adjust as needed
    I.fillField('toAccount', '12'); // Adjust as needed
    I.click('Save Changes');

   
    I.seeInCurrentUrl('/userHistory'); // Adjust if you redirect to a different page

    // Verify the transaction details (you may need to navigate to the relevant page to verify this)
    I.click('User History');
    I.see('10'); // Adjust based on how you display transaction details
});

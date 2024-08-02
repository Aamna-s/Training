Feature('Account Management');

Scenario('Admin Login and Add Account', ({ I }) => {
  // Navigate to the login page
  I.amOnPage('http://localhost:3000/');
  I.see('Login Account');

  // Fill in the login form and submit
  I.fillField('username', 'admin');
  I.fillField('password', 'abc');
  I.click('Login');

  // Wait for navigation to complete
  I.waitForNavigation({ waitUntil: 'networkidle0' });

  // Navigate to the all users page
  I.amOnPage('http://localhost:3000/allUsers');
  I.see('All Users');

  // Click on 'Add User' button
  I.click('Add User');

  // Ensure we are on the create account page
  I.amOnPage('http://localhost:3000/createAccount');
  I.see('Create Account');

  // Fill in the new account form
  I.fillField('username', 'amna');
  I.fillField('password', 'abc');
  I.fillField('role', 'user');
  I.fillField('name', 'AMNA');
  
  // Submit the form
  I.click('Save Changes');

  // Wait for navigation to complete
  I.waitForNavigation({ waitUntil: 'networkidle0' });

  // Verify the new account is added by checking the all users page
  I.amOnPage('http://localhost:3000/allUsers');
  I.see('AMNA');  // Check if the new username is visible in the list
});

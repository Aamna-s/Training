Feature('Edit Account');

Scenario('AdminLogin and Edit Account', ({ I }) => {
    // Step 1: Login as Admin
    I.amOnPage('http://localhost:3000/');
    I.see('Login Account');
    I.fillField('username', 'admin');
    I.fillField('password', 'Abc123');
    I.click('Login');
    I.waitForNavigation({ waitUntil: 'networkidle0' });

    // Step 2: Navigate to All Users page
    I.amOnPage('http://localhost:3000/allUsers');
    I.see('All Users');
    I.click('Edit User');

    // Step 3: Verify Edit Account page is opened
    I.waitForVisible('input[name="name"]'); // Wait for the field to be visible
    I.see('Edit Account');
    I.amOnPage('http://localhost:3000/editAccount/1234567889');

    // Step 4: Edit the account details I.fillField({ id: 'name' }, 'Test');
    I.fillField({ id: 'name' }, 'Updated Name');
    // Step 5: Submit the form
    I.click('Save Changes');
    I.amOnPage('http://localhost:3000/allUsers');
    I.see('Updated Name')

});

Feature('Delete Account');

Scenario('AdminLogin and Delete Account', ({ I }) => {
    // Step 1: Login as Admin
    I.amOnPage('http://localhost:3000/');
    I.see('Login Account');
    I.fillField('username', 'admin');
    I.fillField('password', 'ADMIN');
    I.click('Login');
    I.waitForNavigation({ waitUntil: 'networkidle0' });

    // Step 2: Navigate to All Users page
    I.amOnPage('http://localhost:3000/allUsers');
    I.see('All Users');

    // Step 3: Click on the delete button for a specific user
    // Adjust the selector to target the correct delete button
    I.see('updated')
    I.click('Delete User')


    // Step 5: Verify the user is removed from the list
    I.dontSee('user'); // Ensure that the user no longer appears in the list
});

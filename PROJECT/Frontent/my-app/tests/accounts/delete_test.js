Feature('Delete Account');

Scenario('AdminLogin and Delete Account', ({ I }) => {
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

    // Step 3: Find the row with "Updated Name" and click the delete button
    // Assuming that there is a delete button associated with each user, you should locate it
    I.see('Updated Name'); // Ensure the user is visible
    I.waitForVisible(`//tr[td[contains(text(),'Updated Name')]]//button[contains(@aria-label, 'Delete User')]`);

    // Click the delete button
    I.click(`//tr[td[contains(text(),'Updated Name')]]//button[contains(@aria-label, 'Delete User')]`);




    // Step 4: Confirm the deletion if a confirmation dialog appears
   

    // Step 5: Verify the user is removed from the list
    I.dontSee('Updated Name'); // Ensure that the user no longer appears in the list
});

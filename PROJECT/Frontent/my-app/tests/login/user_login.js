Feature('Login Account');

Scenario('UserLogin ', ({ I }) => {
    // Step 1: Login as Admin
    I.amOnPage('http://localhost:3000/');
    I.see('Login Account');
    I.fillField('username', 'user');
    I.fillField('password', 'Abc123');
    I.click('Login');
    I.waitForNavigation({ waitUntil: 'networkidle0' });
    I.amOnPage('http://localhost:3000/userDashboard');})
Feature('Login Account');

Scenario('AdminLogin ', ({ I }) => {
    // Step 1: Login as Admin
    I.amOnPage('http://localhost:3000/');
    I.see('Login Account');
    I.fillField('username', 'admin');
    I.fillField('password', 'ADMIN');
    I.click('Login');
    I.waitForNavigation({ waitUntil: 'networkidle0' });
    I.amOnPage('http://localhost:3000/home');})
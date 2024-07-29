import React from 'react';
import { Link } from 'react-router-dom';


function Header() {
    return (
        <>
            <div id="layout" className="layout-semiboxed">
                <div id="fond-header" className="fond-header pattern-header-01"></div>
                <header id="header">
                    <div className="row">
                        <div className="col-md-4 col-lg-5">
                            <div className="logo">
                                <div className="icon-logo">
                                    <i className="fa fa-university"></i>
                                </div>
                                <Link to="/">
                                    Coop Bank
                                    <span>Your money is safe.</span>
                                </Link>
                            </div>
                        </div>
                    </div>
                </header>
               
            </div>
        </>
    );
}

export default Header;

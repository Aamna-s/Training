import { Link ,useNavigate} from 'react-router-dom';
import Cookies from 'js-cookie';
function User(){
    const navigate = useNavigate();

    const Logout = () => {
        Cookies.remove('token'); 
        navigate('/');
    };
    return(<>
        <div id="layout" className="layout-semiboxed">
        <div id="navbar-collapse-1" className="navbar-collapse collapse">
        
                                </div>
          <div className="content-central">
          
              
                <section className="content_info">
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
                                    <ul className="nav navbar-nav">
                                        <li className="dropdown">
                                            <Link to="/userDashboard" className="dropdown-toggle">
                                                Home
                                            </Link>
                                        </li>
                                        <li className="dropdown">
                                            <Link to="/transaction" className="dropdown-toggle">
                                                Transaction
                                            </Link>
                                        </li>
                                        <li className="dropdown">
                                            <Link to="/userHistory" className="dropdown-toggle">
                                                User History
                                            </Link>
                                        </li>
                                       
                                        
                                        <li className="dropdown">
                                            <Link to="/viewAccount" className="dropdown-toggle">
                                                View Account
                                            </Link>
                                        </li>
                                        <li className="dropdown" onClick={Logout}>
                                        <Link to="" className="dropdown-toggle">
                                            Logout
                                        </Link>
                                    </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </nav>
                  
                    <div className="content_resalt padding-bottom borders">
                      
                        <div className="title-vertical-line">
                            <h2><span>Last</span> News</h2>
                            <p className="lead">Keep informed and updated on all news related to your bank.</p>
                        </div>
                        <div className="image-container">
                            <img src="images/bank.webp" width="100%" alt="News"/>
                           
                      
                        </div>
                       

                    </div>
                  
                </section>
              
                <div className="parallax-window" data-parallax="scroll" data-image-src="img/parallax-img/parallax-01.jpg">
                  
                    <div className="opacy_bg_02 paddings">
                        <div className="container">
                            <div className="row">
                                    
                                <h1 className="title-downloads">
                                    <span className="logo-clients">CoopBank</span> Has more than
                                    <span className="responsive-numbers">
                                        <span>2</span>
                                        ,
                                        <span>3</span>
                                        <span>8</span>
                                        <span>9</span>
                                        ,
                                        <span>5</span>
                                        <span>1</span>
                                        <span>8</span>
                                    </span>
                                    Clients.
                                </h1>  
                             
                                <div className="subtitle-downloads">
                                    <h4>The best service with the <i className="fa fa-heart"></i></h4>
                                </div> 
                              
                                <ul className="image-clients-downloads">
                                    <li><img src="images/1.jpg" alt=""/></li>
                                    <li><img src="images/2_1.jpg" alt=""/></li>
                                    <li><img src="images/3.jpg" alt=""/></li>
                                    <li><img src="images/4.jpg" alt=""/></li>
                                    <li><img src="images/5.jpg" alt=""/></li>
                                    <li><img src="images/6.jpg" alt=""/></li>
                                    <li><img src="images/7.jpg" alt=""/></li>
                                    <li><img src="images/8.jpg" alt=""/></li>
                                </ul>
                            
                        </div> 
                    </div>  
                   
                </div>
           
                <div className="content_info">
                  
                    <div className="title-vertical-line">
                        <h2><span>Process</span> Services</h2>
                        <p className="lead">We have created alliances with recognized entities that contribute to improving quality of your life.</p>
                    </div>
                  
                    <div className="paddings">
                       
                        <div className="container">
                            <div className="row">
                                <div className="col-md-12">
                             
                                    <div className="services-process">
                                     
                                        <div className="item-service-process color-bg-1">
                                            <div className="head-service-process">
                                                <i className="fa fa-cubes"></i>
                                                <h3>INSURANCES</h3>
                                            </div>
                                            <div className="divisor-service-process">
                                                <span className="circle-top">1</span>
                                                <span className="circle"></span>
                                            </div>
                                            <div className="info-service-process">
                                                <h3>Our Insurances</h3>
                                                <p>Your peace of mind is priceless, we offer a broad portfolio of solutions to ensure what you love most.</p>
                                            </div>
                                        </div>
                                      
                                        <div className="item-service-process color-bg-2">
                                            <div className="head-service-process">
                                                <i className="fa fa-diamond"></i>
                                                <h3>BENEFITS</h3>
                                            </div>
                                            <div className="divisor-service-process">
                                                <span className="circle-top">2</span>
                                                <span className="circle"></span>
                                            </div>
                                            <div className="info-service-process">
                                                <h3>Our Benefits</h3>
                                                <p>We have created alliances with recognized entities that contribute to improving quality of your life.</p>
                                            </div>
                                        </div>
                                       
                                        <div className="item-service-process color-bg-3">
                                            <div className="head-service-process">
                                                <i className="fa fa-bicycle"></i>
                                                <h3>SOCIAL</h3>
                                            </div>
                                            <div className="divisor-service-process">
                                                <span className="circle-top">3</span>
                                                 <span className="circle"></span>
                                            </div>
                                            <div className="info-service-process">
                                                <h3>Social Programs</h3>
                                                <p>Our programs with social sense, everyday comprehensively improve the quality of life of our members.</p>
                                            </div>
                                        </div>
                                 
                                        <div className="item-service-process color-bg-4">
                                            <div className="head-service-process">
                                                <i className="fa fa-hotel"></i>
                                                <h3>RESORTS</h3>
                                            </div>
                                            <div className="divisor-service-process">
                                                <span className="circle-top">4</span>
                                                <span className="circle"></span>
                                            </div>
                                            <div className="info-service-process">
                                                <h3>Resorts Options</h3>
                                                <p>We offer the best alternatives for recreation, relaxation and adventure to share with family and friends.</p>
                                            </div>
                                        </div>
                                    
                                    </div>
                                 
                                </div>
                            </div>
                        </div>
                    
                    </div>
                 
                </div>
              
            </div>
          
           
           
             
          
        </div></div>
       
        
  
       </>)
}
export default User;
function Footer(){
    return(<> <footer id="footer">
                
                
      
        <div className="container">
        <div className="container wow fadeInUp">
                <div className="row">
                    <div className="col-md-12">
                           
                        <ul className="owl-carousel carousel-sponsors tooltip-hover" id="carousel-sponsors">
                            <li data-toggle="tooltip" title="" data-original-title="Name Sponsor">
                                <a href="#" className="tooltip_hover" title="Name Sponsor"><img src="images/1.png" alt="Image"/></a>
                            </li>
                            <li data-toggle="tooltip" title="" data-original-title="Name Sponsor">
                                <a href="#" className="tooltip_hover" title="Name Sponsor"><img src="images/2.png" alt="Image"/></a>
                            </li>
                             <li data-toggle="tooltip" title="" data-original-title="Name Sponsor">
                                <a href="#" className="tooltip_hover" title="Name Sponsor"><img src="images/3.png" alt="Image"/></a>
                            </li>
                            <li data-toggle="tooltip" title="" data-original-title="Name Sponsor">
                                <a href="#" className="tooltip_hover" title="Name Sponsor"><img src="images/4.png" alt="Image"/></a>
                            </li>
                            <li data-toggle="tooltip" title="" data-original-title="Name Sponsor">
                                <a href="#" className="tooltip_hover" title="Name Sponsor"><img src="images/5.png" alt="Image"/></a>
                            </li>
                            <li data-toggle="tooltip" title="" data-original-title="Name Sponsor">
                                <a href="#" className="tooltip_hover" title="Name Sponsor"><img src="images/6.png" alt="Image"/></a>
                            </li>
                            <li data-toggle="tooltip" title="" data-original-title="Name Sponsor">
                                <a href="#" className="tooltip_hover" title="Name Sponsor"><img src="images/7.png" alt="Image"/></a>
                            </li>
                             <li data-toggle="tooltip" title="" data-original-title="Name Sponsor">
                                <a href="#" className="tooltip_hover" title="Name Sponsor"><img src="images/8.png" alt="Image"/></a>
                            </li>                                       
                        </ul> 
                      
                    </div>                    
                </div>
            </div>
            <div className="row paddings-mini">
              
                <div className="col-sm-6 col-md-3">
                    <div className="border-right txt-right">
                        <h4>Contact us</h4>
                        <ul className="contact-footer">
                            <li>
                                <i className="fa fa-envelope"></i> <a href="#"><span className="__cf_email__" data-cfemail="deadbfb2bbad9ebdb1b1aebcbfb0b5f0bdb1b3">[email�&nbsp;protected]</span></a>
                            </li>
                            <li>
                                <i className="fa fa-headphones"></i> <a href="#">55-5698-4589</a>
                             </li>
                            <li className="location">
                                <i className="fa fa-home"></i> <a href="#"> Av new stret - New York</a>
                            </li>                                   
                        </ul>
                        <div className="logo-footer">
                            <div className="icon-logo">
                                <i className="fa fa-university"></i>
                            </div>
                            <a href="index.html">
                                Coop Bank
                                <span>Your money is safe.</span>
                            </a>
                        </div>
                   </div>
                </div>
        
                <div className="col-sm-6 col-md-3">
                      <div className="border-right border-right-none">
                          <h4>Recent Links</h4>
                          <ul className="list-styles">
                              <li><i className="fa fa-check"></i> <a href="#">Corporate Web CoopBank</a></li>
                              <li><i className="fa fa-check"></i> <a href="#">CoopBank Innovation Center</a></li>
                              <li><i className="fa fa-check"></i> <a href="#">Corporate Responsibility</a></li>
                              <li><i className="fa fa-check"></i> <a href="#">Information of interest</a></li>
                          </ul>
                     </div>
                </div>
              
                <div className="col-sm-6 col-md-4">
                    <div className="border-right txt-right">
                        <h4>Newsletter</h4>
                        <p>Please enter your e-mail and subscribe to our newsletter.</p>
                        <form id="newsletterForm" className="newsletterForm" action="php/mailchip/newsletter-subscribe.php">
                            <div className="input-group">
                                <span className="input-group-addon">
                                    <i className="fa fa-envelope"></i>
                                </span>
                                <input className="form-control" placeholder="Email Address" name="email" type="email" required="required"/>
                                <span className="input-group-btn">
                                    <button className="btn btn-primary" type="submit" name="subscribe">Go!</button>
                                </span>
                            </div>
                        </form>   
                        <div id="result-newsletter"></div>    
                   </div>
                </div>
              
                <div className="col-sm-6 col-md-2">
                    <div className="border-right-none">
                      <h4>Follow To CoopBank</h4>
                      <ul className="social">
                          <li className="facebook"><span><i className="fa fa-facebook"></i></span><a href="#">Facebook</a></li>
                          <li className="twitter"><span><i className="fa fa-twitter"></i></span><a href="#">Twitter</a></li>
                          <li className="github"><span><i className="fa fa-github"></i></span><a href="#">Github</a></li>
                          <li className="linkedin"><span><i className="fa fa-linkedin"></i></span><a href="#">Linkedin</a></li>
                      </ul>
                    </div>
                </div>
               
            </div>
        </div>
       
        <div className="footer-down">
            <div className="container">
                <div className="row">
                    <div className="col-md-7">
                        
                        <ul className="nav-footer">
                            <li><a href="#">HOME</a> </li>
                            <li><a href="#">COMPANY</a></li>
                            <li><a href="#">SERVICES</a></li> 
                            <li><a href="#">NEWS</a></li> 
                            <li><a href="#">PORTFOLIO</a></li>                
                            <li><a href="#">CONTACT</a></li>
                        </ul>
                     
                    </div>
                    <div className="col-md-5">
                        <p>© 2015 CoopBank. All Rights Reserved.  2010 - 2015</p>
                    </div>
                </div>
            </div>
        </div>
      
    </footer>    </>)

}
export default Footer;
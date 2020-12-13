import React, { Component } from 'react'

interface AuthorProps {
}

interface AuthorState {
}

class Author extends Component<AuthorProps, AuthorState> {

    render() {
        return (
            <div>
                <h1><span className="badge badge-pill badge-primary align-items-centre">Add Author</span></h1>
                <div className="container">
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="firstName">First name</label>
                            <input type="text" className="form-control" id="firstName" />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="lastName">Last name</label>
                            <input type="text" className="form-control" id="lastName" />
                        </div>
                    </div>   
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="address">Address</label>
                            <input type="text" className="form-control" id="address" />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="city">City</label>
                            <input type="text" className="form-control" id="city" />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="state">State</label>
                            <input type="text" className="form-control" id="state" />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="zipCode">Zip code</label>
                            <input type="text" className="form-control" id="zipCode" />
                        </div>
                    </div>    
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="country">Country</label>
                            <input type="text" className="form-control" id="country" />
                        </div>
                    </div>  
                    <button type="button" className="btn btn-primary">Submit</button>                                                                                                                
                </div>
            </div>
        );
    }

}

export default Author;

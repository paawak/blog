import React, { ChangeEvent, Component } from 'react'

interface AuthorProps {
}

interface AuthorState {
    firstName: string,
    lastName: string,
    address: string,
    city: string,
    state: string,
    zipCode: string,
    country: string
}

class Author extends Component<AuthorProps, AuthorState> {

    handleSubmit = () => {
        console.log('*****', this.state);
    }

    handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        console.log('*****', event.target.id, event.target.value);
        this.setState({
            firstName: event.target.value
          });
    }

    render() {
        return (
            <div>
                <h1><span className="badge badge-pill badge-primary align-items-centre">Add Author</span></h1>
                <div className="container">
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="firstName">First name</label>
                            <input type="text" className="form-control" id="firstName" onChange={this.handleChange} />
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
                    <button type="button" className="btn btn-primary" onClick={this.handleSubmit}>Submit</button>                                                                                                                
                </div>
            </div>
        );
    }

}

export default Author;

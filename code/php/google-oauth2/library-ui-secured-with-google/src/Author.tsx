import React, { Component } from 'react'
import Alert from './Alert';
import AlertType from './AlertType';

interface AuthorProps {
    googleAccessToken: string
}

interface AuthorState {
    firstName: string,
    lastName: string,
    address: string,
    city: string,
    state: string,
    zipCode: string,
    country: string,
    authorAddSuccess: boolean,
    authorAddFailed: boolean
}

class Author extends Component<AuthorProps, AuthorState> {

    state: AuthorState = {
        firstName: '',
        lastName: '',
        address: '',
        city: '',
        state: '',
        zipCode: '',
        country: '',
        authorAddSuccess: false,
        authorAddFailed: false
    };

    handleSubmit = () => {
        const authorPayload = {
            "firstName": this.state.firstName,
            "lastName": this.state.lastName,
            "address": {
                "address": this.state.address,
                "city": this.state.city,
                "state": this.state.state,
                "zipCode": this.state.zipCode,
                "country": this.state.country
            }
        };
        fetch(`${process.env.REACT_APP_REST_API_BASE_NAME}/author`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': this.props.googleAccessToken
            },
            body: JSON.stringify(authorPayload)
        }).then(response => {
            if (response.ok) {
                this.setState({authorAddSuccess: true});
            } else {
                this.setState({authorAddFailed: true});
            }
        });
    }

    render() {
        return (
            <div>
                <h1><span className="badge badge-pill badge-primary align-items-centre">Add Author</span></h1>
                {
                    this.state.authorAddFailed &&
                    <Alert type={AlertType.ERROR} message='Could not save the Author, please try again.'/>
                }
                {
                    this.state.authorAddSuccess &&
                    <Alert type={AlertType.SUCCESS} message='Author saved successfully.'/>
                }                
                <div className="container">
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="firstName">First name</label>
                            <input type="text" className="form-control" id="firstName" value={this.state.firstName} onChange={(event) => { this.setState({ firstName: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="lastName">Last name</label>
                            <input type="text" className="form-control" id="lastName" value={this.state.lastName} onChange={(event) => { this.setState({ lastName: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="address">Address</label>
                            <input type="text" className="form-control" id="address" value={this.state.address} onChange={(event) => { this.setState({ address: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="city">City</label>
                            <input type="text" className="form-control" id="city" value={this.state.city} onChange={(event) => { this.setState({ city: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="state">State</label>
                            <input type="text" className="form-control" id="state" value={this.state.state} onChange={(event) => { this.setState({ state: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="zipCode">Zip code</label>
                            <input type="text" className="form-control" id="zipCode" value={this.state.zipCode} onChange={(event) => { this.setState({ zipCode: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="country">Country</label>
                            <input type="text" className="form-control" id="country" value={this.state.country} onChange={(event) => { this.setState({ country: event.target.value }); }} />
                        </div>
                    </div>
                    <button type="button" className="btn btn-primary" onClick={this.handleSubmit}>Submit</button>
                </div>
            </div>
        );
    }

}

export default Author;

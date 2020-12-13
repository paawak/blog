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
                            <label htmlFor="exampleInputEmail1">Email address</label>
                            <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" />
                            <small id="emailHelp" className="form-text text-muted">We'll never share your email with anyone else.</small>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

}

export default Author;

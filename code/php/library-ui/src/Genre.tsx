import React, { Component } from 'react'
import { Alert } from './Alert';
import AlertType from './AlertType';

interface GenreProps {
}

interface GenreState {
    name: string,
    shortName: string,
    genreAddSuccess: boolean,
    genreAddFailed: boolean
}

class Genre extends Component<GenreProps, GenreState> {

    state: GenreState = {
        name: '',
        shortName: '',
        genreAddSuccess: false,
        genreAddFailed: false
    };

    handleSubmit = () => {
        const GenrePayload = {
            "name": this.state.name,
            "shortName": this.state.shortName,
        };
        fetch(`${process.env.REACT_APP_REST_API_BASE_NAME}/genre`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(GenrePayload)
        }).then(response => {
            if (response.ok) {
                this.setState({genreAddSuccess: true});
            } else {
                this.setState({genreAddFailed: true});
            }
        });
    }

    render() {
        return (
            <div>
                <h1><span className="badge badge-pill badge-primary align-items-centre">Add Genre</span></h1>
                {
                    this.state.genreAddFailed &&
                    <Alert type={AlertType.ERROR} message='Could not save the Genre, please try again.'/>
                }
                {
                    this.state.genreAddSuccess &&
                    <Alert type={AlertType.SUCCESS} message='Genre saved successfully.'/>
                }
                <div className="container">
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="name">Name</label>
                            <input type="text" className="form-control" id="name" value={this.state.name} onChange={(event) => { this.setState({ name: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="shortName">Short name</label>
                            <input type="text" className="form-control" id="lastName" value={this.state.shortName} onChange={(event) => { this.setState({ shortName: event.target.value }); }} />
                        </div>
                    </div>                    
                    <button type="button" className="btn btn-primary" onClick={this.handleSubmit}>Submit</button>
                </div>
            </div>
        );
    }

}

export default Genre;

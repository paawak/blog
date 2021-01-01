import React, { Component } from 'react'

interface BookProps {
}

interface BookState {
    title: string,
    authorId: string,
    genreId: string
}

class Book extends Component<BookProps, BookState> {

    state: BookState = {
        title: '',
        authorId: '',
        genreId: ''
    };

    handleSubmit = () => {
        const BookPayload = {
            "title": this.state.title,
            "author": {
                "id": this.state.authorId
            },
            "genre": {
                "id": this.state.genreId
            }   
        };
        fetch(`${process.env.REACT_APP_REST_API_BASE_NAME}/book`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(BookPayload)
        }).then(response => {
            if (response.ok) {
                console.log('----', response)
            } else {
                // display error
                console.error('Error', response);
            }
        });
    }

    render() {
        return (
            <div>
                <h1><span className="badge badge-pill badge-primary align-items-centre">Add Book</span></h1>
                <div className="container">
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="title">Title</label>
                            <input type="text" className="form-control" id="title" value={this.state.title} onChange={(event) => { this.setState({ title: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="authorId">Author</label>
                            <input type="text" className="form-control" id="authorId" value={this.state.authorId} onChange={(event) => { this.setState({ authorId: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="genreId">Genre</label>
                            <input type="text" className="form-control" id="genreId" value={this.state.genreId} onChange={(event) => { this.setState({ genreId: event.target.value }); }} />
                        </div>
                    </div>                    
                    <button type="button" className="btn btn-primary" onClick={this.handleSubmit}>Submit</button>
                </div>
            </div>
        );
    }

}

export default Book;

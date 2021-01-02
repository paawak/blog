import React, { Component } from 'react'

interface BookProps {
}

interface BookState {
    title: string,
    selectedAuthorId: string,
    selectedGenreId: string,
    authors: ComboBoxItemValue[],
    genres: ComboBoxItemValue[]
}

interface ComboBoxItemValue {
    itemId: string,
    displayText: string
}

class Book extends Component<BookProps, BookState> {

    state: BookState = {
        title: '',
        selectedAuthorId: '',
        selectedGenreId: '',
        authors: [],
        genres: []
    };

    componentDidMount() {
        fetch(`${process.env.REACT_APP_REST_API_BASE_NAME}/genre`)
            .then(response => response.json())
            .then(rawGenres => {
                const genres: ComboBoxItemValue[] = rawGenres.map((rawGenre: any) => {
                    return {
                        itemId: rawGenre.id,
                        displayText: rawGenre.name
                    } as ComboBoxItemValue;
                });

                this.setState({ genres: genres });

                console.log('11111111', genres);
            });

        fetch(`${process.env.REACT_APP_REST_API_BASE_NAME}/author`)
            .then(response => response.json())
            .then(rawAuthors => {
                const authors: ComboBoxItemValue[] = rawAuthors.map((rawAuthor: any) => {
                    return {
                        itemId: rawAuthor.id,
                        displayText: rawAuthor.firstName + ' ' + rawAuthor.lastName
                    } as ComboBoxItemValue;
                });

                this.setState({ authors: authors });

                console.log('22222222222', authors);
            });

    }

    handleSubmit = () => {
        const BookPayload = {
            "title": this.state.title,
            "author": {
                "id": this.state.selectedAuthorId
            },
            "genre": {
                "id": this.state.selectedGenreId
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
                            <input type="text" className="form-control" id="authorId" value={this.state.selectedAuthorId} onChange={(event) => { this.setState({ selectedAuthorId: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="genreId">Genre</label>
                            <input type="text" className="form-control" id="genreId" value={this.state.selectedGenreId} onChange={(event) => { this.setState({ selectedGenreId: event.target.value }); }} />
                        </div>
                    </div>
                    <button type="button" className="btn btn-primary" onClick={this.handleSubmit}>Submit</button>
                </div>
            </div>
        );
    }

}

export default Book;

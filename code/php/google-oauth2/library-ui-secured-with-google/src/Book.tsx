import React, { Component } from 'react'
import Alert from './Alert';
import AlertType from './AlertType';

interface BookProps {
    googleAccessToken: string
}

interface BookState {
    title: string,
    selectedAuthorId: string,
    selectedGenreId: string,
    authors: ComboBoxItemValue[],
    genres: ComboBoxItemValue[],
    noAuthorsFound: boolean,
    noGenresFound: boolean,
    bookAddSuccess: boolean,
    bookAddFailed: boolean
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
        genres: [],
        noAuthorsFound: true,
        noGenresFound: true,
        bookAddSuccess: false,
        bookAddFailed: false
    };

    componentDidMount() {
        fetch(`${process.env.REACT_APP_REST_API_BASE_NAME}/genre`, {
            headers: {
                'Authorization': this.props.googleAccessToken
            }
        }).then(response => response.json())
            .then(rawGenres => {
                const genres: ComboBoxItemValue[] = rawGenres.map((rawGenre: any) => {
                    return {
                        itemId: rawGenre.id,
                        displayText: rawGenre.name
                    } as ComboBoxItemValue;
                });

                this.setState({
                    genres: genres,
                    noGenresFound: genres.length === 0
                });
            });

        fetch(`${process.env.REACT_APP_REST_API_BASE_NAME}/author`, {
            headers: {
                'Authorization': this.props.googleAccessToken
            }
        }).then(response => response.json())
            .then(rawAuthors => {
                const authors: ComboBoxItemValue[] = rawAuthors.map((rawAuthor: any) => {
                    return {
                        itemId: rawAuthor.id,
                        displayText: rawAuthor.firstName + ' ' + rawAuthor.lastName
                    } as ComboBoxItemValue;
                });

                this.setState({
                    authors: authors,
                    noAuthorsFound: authors.length === 0
                });
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
                this.setState({ bookAddSuccess: true });
            } else {
                this.setState({ bookAddFailed: true });
            }
        });
    }

    render() {
        return (
            <div>
                <h1><span className="badge badge-pill badge-primary align-items-centre">Add Book</span></h1>
                {
                    this.state.bookAddFailed &&
                    <Alert type={AlertType.ERROR} message='Could not save the Book, please try again.' />
                }
                {
                    this.state.bookAddSuccess &&
                    <Alert type={AlertType.SUCCESS} message='Book saved successfully.' />
                }
                <div className="container">
                    <div className="row align-items-start">
                        <div className="col form-group">
                            <label htmlFor="title">Title</label>
                            <input type="text" className="form-control" id="title" value={this.state.title} onChange={(event) => { this.setState({ title: event.target.value }); }} />
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            {this.state.noAuthorsFound &&
                                <Alert type={AlertType.ERROR} message='No authors found: first add an Author' />
                            }
                            <label htmlFor="authorId">Author</label>
                            <select className="custom-select" id="authorId" onChange={(event) => { this.setState({ selectedAuthorId: event.target.value }); }} >
                                <option value=''>---</option>
                                {
                                    this.state.authors.map((author: ComboBoxItemValue) => {
                                        return (
                                            <option value={author.itemId} selected={this.state.selectedAuthorId === author.itemId}>{author.displayText}</option>
                                        );
                                    })
                                }
                            </select>
                        </div>
                    </div>
                    <div className="row align-items-start">
                        <div className="col form-group">
                            {this.state.noGenresFound &&
                                <Alert type={AlertType.ERROR} message='No genres found: first add a Genre' />
                            }
                            <label htmlFor="genreId">Genre</label>
                            <select className="custom-select" id="genreId" onChange={(event) => { this.setState({ selectedGenreId: event.target.value }); }} >
                                <option value=''>---</option>
                                {
                                    this.state.genres.map((genre: ComboBoxItemValue) => {
                                        return (
                                            <option value={genre.itemId} selected={this.state.selectedGenreId === genre.itemId}>{genre.displayText}</option>
                                        );
                                    })
                                }
                            </select>
                        </div>
                    </div>
                    <button type="button" disabled={this.state.selectedAuthorId === '' || this.state.selectedGenreId === ''} className="btn btn-primary" onClick={this.handleSubmit}>Submit</button>
                </div>
            </div>
        );
    }

}

export default Book;

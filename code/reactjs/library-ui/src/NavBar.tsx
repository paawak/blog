import React, { Component } from 'react'
import MenuAction from './MenuAction';
import MenuActionListener from './MenuActionListener';

interface NavBarProps {
    menuActionListener: MenuActionListener;
}

interface NavBarState {

}

class NavBar extends Component<NavBarProps, NavBarState> {

    state: NavBarState = {

    };

    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#genreDropDown" aria-controls="genreDropDown" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#authorDropDown" aria-controls="authorDropDown" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#bookDropDown" aria-controls="bookDropDown" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="genreDropDown">
                    <ul className="navbar-nav">
                        <li className="nav-item dropdown">
                            <a className="nav-link dropdown-toggle" href="#noLink" id="genreDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Genre
                            </a>
                            <div className="dropdown-menu" aria-labelledby="genreDropdownMenuLink">
                                <a className="dropdown-item" href="#noLink" onClick={e => this.props.menuActionListener(MenuAction.SHOW_ALL_GENRES)} >Show All</a>
                                <a className="dropdown-item" href="#noLink" onClick={e => this.props.menuActionListener(MenuAction.ADD_NEW_GENRE)} >Add New</a>
                            </div>
                        </li>
                        <li className="nav-item dropdown">
                            <a className="nav-link dropdown-toggle" href="#noLink" id="authorDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Author
                            </a>
                            <div className="dropdown-menu" aria-labelledby="authorDropdownMenuLink">
                                <a className="dropdown-item" href="#noLink" onClick={e => this.props.menuActionListener(MenuAction.SHOW_ALL_AUTHORS)} >Show All</a>
                                <a className="dropdown-item" href="#noLink" onClick={e => this.props.menuActionListener(MenuAction.ADD_NEW_AUTHOR)} >Add New</a>
                            </div>
                        </li>
                        <li className="nav-item dropdown">
                            <a className="nav-link dropdown-toggle" href="#noLink" id="bookDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Book
                            </a>
                            <div className="dropdown-menu" aria-labelledby="bookDropdownMenuLink">
                                <a className="dropdown-item" href="#noLink" onClick={e => this.props.menuActionListener(MenuAction.SHOW_ALL_BOOKS)} >Show All</a>
                                <a className="dropdown-item" href="#noLink" onClick={e => this.props.menuActionListener(MenuAction.ADD_NEW_BOOK)} >Add New</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        );
    }

}

export default NavBar;

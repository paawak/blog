/* eslint-disable jsx-a11y/anchor-is-valid */
import React, { FunctionComponent } from 'react'
import MenuAction from './MenuAction';
import MenuActionListener from './MenuActionListener';

interface NavBarProps {
    menuActionListener: MenuActionListener;
}

const NavBar: FunctionComponent<NavBarProps> = ({ menuActionListener }) => {
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
                <ul className="navbar-nav  mr-auto mt-2 mt-lg-0">
                    <li className="nav-item dropdown">
                        <a className="nav-link dropdown-toggle" href="#" id="genreDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Genre
                            </a>
                        <div className="dropdown-menu" aria-labelledby="genreDropdownMenuLink">
                            <a className="dropdown-item" href="#" onClick={e => menuActionListener(MenuAction.SHOW_ALL_GENRES)} >Show All</a>
                            <a className="dropdown-item" href="#" onClick={e => menuActionListener(MenuAction.ADD_NEW_GENRE)} >Add New</a>
                        </div>
                    </li>
                    <li className="nav-item dropdown">
                        <a className="nav-link dropdown-toggle" href="#" id="authorDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Author
                            </a>
                        <div className="dropdown-menu" aria-labelledby="authorDropdownMenuLink">
                            <a className="dropdown-item" href="#" onClick={e => menuActionListener(MenuAction.SHOW_ALL_AUTHORS)} >Show All</a>
                            <a className="dropdown-item" href="#" onClick={e => menuActionListener(MenuAction.ADD_NEW_AUTHOR)} >Add New</a>
                        </div>
                    </li>
                    <li className="nav-item dropdown">
                        <a className="nav-link dropdown-toggle" href="#" id="bookDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Book
                            </a>
                        <div className="dropdown-menu" aria-labelledby="bookDropdownMenuLink">
                            <a className="dropdown-item" href="#" onClick={e => menuActionListener(MenuAction.SHOW_ALL_BOOKS)} >Show All</a>
                            <a className="dropdown-item" href="#" onClick={e => menuActionListener(MenuAction.ADD_NEW_BOOK)} >Add New</a>
                        </div>
                    </li>
                </ul>
                <div>
                    <ul className="navbar-nav">
                        <li className="nav-item dropdown">
                            <a className="nav-link dropdown-toggle" href="#" id="userProfile" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <img src="/person-circle.svg" alt="" width="52" height="32" title="UserProfile"></img>
                            </a>
                            <div className="dropdown-menu" aria-labelledby="userProfile">
                                <a className="dropdown-item" href="#">Log Off</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default NavBar;

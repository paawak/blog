import React, { FunctionComponent } from 'react';

type AlertProps = {
    message: string
}

export const Alert: FunctionComponent<AlertProps> = ({ message }) =>
    <div className="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>Warning!</strong> {message}
        <button type="button" className="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>

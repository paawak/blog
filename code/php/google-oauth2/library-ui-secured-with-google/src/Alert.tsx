import React, { FunctionComponent } from 'react';
import AlertType from './AlertType';

type AlertProps = {
    type: AlertType,
    message: string
}

const Alert: FunctionComponent<AlertProps> = ({ type, message }) => {
    let alertClassName = 'alert alert-dismissible fade show ';
    let title = '';

    if (type === AlertType.SUCCESS) {
        alertClassName += 'alert-success';
        title = 'Success!';
    } else if (type === AlertType.WARNING) {
        alertClassName += 'alert-warning';
        title = 'Warning!';
    } else if (type === AlertType.ERROR) {
        alertClassName += 'alert-danger';
        title = 'Error!';
    }

    return (
        <div className={alertClassName} role="alert">
            <strong>{title}</strong> {message}
            <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    );
};

export default Alert;

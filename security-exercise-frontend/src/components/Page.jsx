import React, { useState } from 'react';
import Login from './Login';

export default function Page(props) {
    if(!props.user.username) {
        return (
            <>
            <Login update={props.update}/>
            </>
        )
    }else {
        return(
            <>
            <p>{props.user.username} is logged in with token</p>
            <p>{props.user.token}</p>
            </>
        )
    }
}
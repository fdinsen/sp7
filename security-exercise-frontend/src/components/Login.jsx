import React, { useState } from 'react';
import utils from './utils';

export default function Login(props) {
    const initialValue = {
        username: "",
        password: ""
    }
    const [user, setUser] = useState(initialValue);

    function handleChange(event) {
        const target = event.target;
        const value = target.type === "checkbox" ? target.checked : target.value;
        const name = target.name;
        setUser({ ...user, [name]: value });
    }

    function handleSubmit(event) {
        event.preventDefault();
        doLogin(user, props.update);
    }

    return (
        <>
            <div className="login">
                <h1>Login</h1>
                <form onSubmit={handleSubmit}>
                    <input type="text" value={user.username} onChange={handleChange} placeholder="Enter Username" name="username" />
                    <input type="password" value={user.password} onChange={handleChange} placeholder="Enter password" name="password" />
                    <input type="submit" value="Login"></input>
                </form>
            </div>
        </>
    );
}

async function doLogin(user, update) {
    const url = utils.defaultUrl + "api/login";
    console.log(url);
    utils.fetchAny(url, (res) => {
        update(res);

    }, "POST", user);
}
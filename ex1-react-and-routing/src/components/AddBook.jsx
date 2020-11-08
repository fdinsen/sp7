import React, { useState } from "react";
import {Prompt} from 'react-router-dom';
export default function AddBook(props) {
    const initialValue = {
        title: "",
        info: ""
    }
    const [book, setBook] = useState(initialValue);
    const [isBlocking, setIsBlocking] = useState(false);

    const handleChange = (event) => {
        let target = event.target;
        //If target type is a checkbox, value = checked-state, else value = text-value
        const value = target.type === "checkbox" ? target.checked : target.value;
        const name = target.name;

        setBook({ ...book, [name]: value });

        //If anything has been typed into the text-box, is blocking is true
        //this intoduces a bug where if one field has been emptied, no prompt will be displayed 
        setIsBlocking(event.target.value.length > 0);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        props.bookFacade.addBook(book);
        alert(`${book.title} added!`);
        event.target.reset();
        setIsBlocking(false);
    }
    return (
        <>
            <h1>Add Book</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="title">Title</label>
                <input type="text" value={book.name} onChange={handleChange} placeholder="Title" name="title" />
                <br />
                <label htmlFor="info">Info</label>
                <input type="text" value={book.info} onChange={handleChange} placeholder="Info" name="info" />
                <br />
                <input type="submit" value="Save"/>
            </form>

            <p>id: {book.id}, title: {book.title}, info: {book.info}</p>

            <Prompt
                when={isBlocking}
                message={location =>
                    `Are you sure you want to go to ${location.pathname}`
                }
            />
        </>
    );
}
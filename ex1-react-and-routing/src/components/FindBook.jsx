import React, { useState } from "react";
export default function FindBook(props) {
    const [value, setValue] = useState("");
    const [bookId, setBookId] = useState("");
    

    function handleChange(event) {
        const target = event.target;
        const value = target.type === "checkbox" ? target.checked : target.value;
        setValue(value);
    }

    function handleSubmit(event) {
        event.preventDefault();
        setBookId(value);
    }

    function resetHook(){
        setBookId("");
        setValue("");
    }
    return (
        <>
            <h1>Find Book</h1>
            <form onSubmit={handleSubmit}>
                <input type="number" value={value} onChange={handleChange} placeholder="Enter book ID" />
                <input type="submit" value="Find" />
            </form>
            <br/>
            <DisplayBook bookId={bookId} bookFacade={props.bookFacade} reset={resetHook}/>
        </>
    );
}

function DisplayBook(props) {
    function handleDelete(event) {
        props.bookFacade.deleteBook(props.bookId);
        props.reset();
    }

    //returns
    if(props.bookId === "") {
        return (
            <>
                <div>Enter a book id</div>
            </>
        );
    }
    try {
        const book = props.bookFacade.findBook(props.bookId);
        return (
            <>
                <div className="details-box">
                    <p>Title: {book.title}</p>
                    <p>Info: {book.info}</p>
                    <p>ID: {book.id}</p>
                    <button onClick={handleDelete}>Delete book</button>
                </div>
            </>
        );
    }catch (e) {
        return (
            <>
                <div className="details-box">
                    <p>No book found by id {props.bookId}</p>
                </div>
            </>
        )
    }
    
}
import React from 'react';
import './App.css';
import TodoLists from "./todolist/TodoLists";

const App: React.FC = () => {

    return (
        <div className="App">
            <header className="App-header">
                <TodoLists/>
            </header>
        </div>
    );
}

export default App;

import React from 'react'
import {Route, Routes} from "react-router-dom";
import {Simulation} from "./Simulation.tsx";

export const App = () : React.JSX.Element =>  {
    return (
        <>
            <Routes>
                <Route path={"/"} element={<Simulation/>}/>
            </Routes>
        </>
    )
}

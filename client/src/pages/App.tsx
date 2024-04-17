import React from 'react'
import {Route, Routes} from "react-router-dom";
import {Config} from "./Config.tsx";
import {Simulation} from "./Simulation.tsx";

export const App = () : React.JSX.Element =>  {
    return (
        <>
            <Routes>
                <Route path={"/"} element={<Config/>}/>
                <Route path={"/result"} element={<Simulation/>}/>
            </Routes>
        </>
    )
}

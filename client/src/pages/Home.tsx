import {useNavigate} from "react-router-dom";

export const Home = () => {
    const navigate = useNavigate()

    return (
        <div className="relative h-screen w-screen bg-cover bg-center bg-cyan-950 flex items-center">
            <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full" onClick={() => navigate("/simulation")}>START!</button>
        </div>
    )
}
import {useNavigate} from "react-router-dom";
import classNames from "classnames";

export const Home = () => {
    const navigate = useNavigate()

    return (
        <div className="relative h-screen w-screen bg-cover bg-center bg-cyan-950">
            <button className={classNames(
                        "absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2",
                        "bg-cyan-800 ring-8 ring-opacity-25 shadow-2xl",
                        "ring-cyan-700 rounded-full p-28 text-center animate-pulse",
                        "hover:animate-none"
                    )}
                    onClick={() => navigate("/simulation")}>
                <div className={classNames("text-xl text-white font-black")}>START!</div>
            </button>
        </div>
    )
}
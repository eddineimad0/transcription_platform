const spinner = document.querySelector(".loader");
const downloadGroup = document.getElementById("download-group");
const urlForm = document.querySelector(".form");

const submitHandler = async (e)=>{
    e.preventDefault();
    const urlInput = urlForm.querySelector("#video-url");
    const videoUrl = urlInput.value;
    urlForm.classList.add("disabled");
    spinner.classList.remove("disabled");
    const api = `/transcribe?url=${videoUrl}`;
    const response = await fetch(api,{
        method:'GET'
    });
    const videoId = await response.text();
    const link = downloadGroup.querySelector("a");
    const action = `/download/${videoId}`;
    link.download = videoId;
    link.href = action;
    spinner.classList.add("disabled");
    downloadGroup.classList.remove("disabled");
}
urlForm.addEventListener("submit",submitHandler);
// Firebase configuration
var firebaseConfig = {
	apiKey: "AIzaSyC8-eqKHvHf_dRxOlAFOiv_KH3DNvxLjcE",
	authDomain: "kch-mobile-app.firebaseapp.com",
	databaseURL: "https://kch-mobile-app.firebaseio.com",
	projectId: "kch-mobile-app",
	storageBucket: "kch-mobile-app.appspot.com",
	messagingSenderId: "1055595328709",
	appId: "1:1055595328709:web:fd5d31a810974844fe2cac",
	measurementId: "G-BWE5EKTWPS"
};


// Initialize Firebase
firebase.initializeApp(firebaseConfig);
firebase.analytics();
firebase.auth.Auth.Persistence.SESSION;
var db = firebase.firestore();
const storage = firebase.storage();


//auth.html
//Authentication 
function authLogin() {
	
	var email = $("#email").val();
	var password = $("#password").val();

	if (email != "" && password != "") {

		var result = firebase.auth().signInWithEmailAndPassword(email, password);
		result.catch(function(error) {
			var errorCode = error.code;
			var errorMsg = error.message;
			window.alert(errorMsg);
		});

	}
	else {
		window.alert("Please fill out the field.");
	}
}

function loginKey() {
	if (event.keyCode === 13) {
		authLogin();
	}
}

function authLogout() {
	Swal.fire({
	  title: 'Logout?',
	  text: 'You will be logged out of this account.',
	  icon: 'warning',
	  showCancelButton: true,
	  confirmButtonText: 'Yes',
	  cancelButtonText: 'No',
	  allowOutsideClick: false,
  	  allowEscapeKey: false
	}).then((result) => {
	  if (result.value) {
		firebase.auth().signOut();
	  } else if (result.dismiss === Swal.DismissReason.cancel) {

	  }
	});
	
}

function togglePassword() {
	var visiblePass = document.getElementById("eye");
	var notVisiblePass = document.getElementById("eye-slash");
	var pass = document.getElementById("password");

	if (pass.type == 'password') {
		pass.type = "text";
		visiblePass.style.display = "block";
		notVisiblePass.style.display = "none";
	}
	else {
		pass.type = "password";
		visiblePass.style.display = "none";
		notVisiblePass.style.display = "block";
	}

}

//reset.html
function resetPassword() {
	var auth = firebase.auth();
	var email = document.getElementById("reset-email").value;

	if(email != "") {
		auth.sendPasswordResetEmail(email).then(function() {
			 Swal.fire(
		      'Email sent!',
		      'Please check your email to verify.',
		      'success'
		    );
			 Swal.fire({
			  title: 'Email sent!',
			  text: 'Please check your email to verify.',
			  icon: 'success',
			  confirmButtonText: 'Proceed to login page',
			  allowOutsideClick: false,
		  	  allowEscapeKey: false
			}).then((result) => {
			  if (result.value) {
				 window.open("auth.html", "_self");
			  } else if (result.dismiss === Swal.DismissReason.cancel) {

			  }
	});
		})
		.catch(function(error) {
			var errorCode = error.code;
			var errorMsg = error.message;	
		});
	}
	else if(email == "") {
		window.alert("Please fill out the field.");
	}
}



//index.html
//Web pre-loader
var loader;
function loadNow(opacity) {
	if (opacity <= 0) {
		displayContent();
	}
	else {
		loader.style.opacity = opacity; 
		window.setTimeout(function() {
			loadNow(opacity - 0.08)
		}, 100);
	}
}

function displayContent() {
	loader.style.display = "none";
	document.getElementById("content").style.display = "block";
}

document.addEventListener("DOMContentLoaded", function() {
	loader = document.getElementById("container-splash");
	loadNow(2.5);
});

//Uploading images
const coverArt = document.getElementById("vid_cover");
const thumbnail = document.getElementById("vid_thumbnail");
const previewCover = document.getElementById("img-cover");
const previewThumb = document.getElementById("img-thumb");
const previewImgCover = previewCover.querySelector(".image-preview-cover");
const previewDefTextCover = previewCover.querySelector(".preview-def-text-cover");
const previewImgThumb = previewThumb.querySelector(".image-preview-thumb");
const previewDefTextThumb = previewThumb.querySelector(".preview-def-text-thumb");
var selectedCoverArt;
var selectedThumbnail;
var coverRef;
var thumbRef;

coverArt.addEventListener("change", function() {
	selectedCoverArt = this.files[0];
	if (selectedCoverArt) {
		const reader = new FileReader();
		previewDefTextCover.style.display = "none";
		previewImgCover.style.display = "block";

		reader.addEventListener("load", function() {
			previewImgCover.setAttribute("src", this.result);
		});

		reader.readAsDataURL(selectedCoverArt);
	}
	else {
		previewDefTextCover.style.display = null;
		previewImgCover.style.display = null;
	}
});

thumbnail.addEventListener("change", function() {
	selectedThumbnail = this.files[0];
	if (selectedThumbnail) {
		const reader = new FileReader();
		previewDefTextThumb.style.display = "none";
		previewImgThumb.style.display = "block";

		reader.addEventListener("load", function() {
			previewImgThumb.setAttribute("src", this.result);
		});

		reader.readAsDataURL(selectedThumbnail);
	}
	else {
		previewDefTextThumb.style.display = null;
		previewImgThumb.style.display = null;
	}
});

function uploadCoverArt() {

	var storageRef = firebase.storage().ref("/CoverArt/"+ selectedCoverArt.name);
	var uploadTask = storageRef.put(selectedCoverArt);
	var coverProgress = document.getElementById("progress-cover");
	
	uploadTask.on("state_changed", function(snapshot) {

		var coverStatus = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
		console.log("upload is " + coverStatus + "% done");
		coverProgress.style.width = `${coverStatus}%`;
		$("#percentage-cover").text(coverStatus.toFixed(0) + "% uploaded");

		
		switch(snapshot.state) {
			case firebase.storage.TaskState.PAUSED:
				console.log("Upload is paused");
				break;
			case firebase.storage.TaskState.RUNNING:
				console.log("Upload is running");
				break;
		}

	}, function(error) {

	}, function() {
			uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
				console.log("File avaiable at", downloadURL);
				coverRef = downloadURL;
			});
	});

}

function uploadThumbnail() {

	var storageRef = firebase.storage().ref("/Thumbnails/" + selectedThumbnail.name);
	var uploadTask = storageRef.put(selectedThumbnail);
	var thumbProgress = document.getElementById("progress-thumbnail");

	
	uploadTask.on("state_changed", function(snapshot) {

		var thumbStatus = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
		console.log("upload is " + thumbStatus + "% done");
		thumbProgress.style.width = `${thumbStatus}%`;
		$("#percentage-thumbnail").text(thumbStatus.toFixed(0) + "% uploaded");

		switch(snapshot.state) {
			case firebase.storage.TaskState.PAUSED:
				console.log("Upload is paused");
				break;
			case firebase.storage.TaskState.RUNNING:
				console.log("Upload is running");
				break;
		}

	}, function(error) {

	}, function() {
			uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
				console.log("File avaiable at", downloadURL);
				thumbRef = downloadURL;
				
			});
	});


}



//Uploading Video

var videoFile = document.getElementById("video-file");
var previewVideo = document.getElementById("container-vid-prev");
var previewShowVideo = previewVideo.querySelector(".video-preview");
var previewDefTextVideo = previewVideo.querySelector(".preview-def-text-video");
var uploadProgress = document.getElementById("upload-progress");
var message = document.getElementById("msg");
var selectedVideoFile;
var vidRef;

$(function() {

	videoFile.addEventListener("change", function() {
		let xhr = new XMLHttpRequest(),
		fd = new FormData;

		fd.append("file", videoFile.files[0]);

		xhr.upload.onloadstart = function(e) {
			uploadProgress.classList.add("visible");
			uploadProgress.value = 0;
			uploadProgress.max = e.total;
		};

		xhr.upload.onprogress = function(e) {
			uploadProgress.value = e.loaded;
			uploadProgress.max = e.total;
		};

		xhr.upload.onloadend = function(e) {
			uploadProgress.classList.remove("visible");
		};

		xhr.onload = function() {
		};

		xhr.open('POST', 'action.php', true);
		xhr.send(fd);

		selectedVideoFile = this.files[0];
		var reader = new FileReader();
		reader.onload = viewer.load;
		reader.readAsDataURL(selectedVideoFile);
		viewer.setProperties(selectedVideoFile);
	});
		
	var viewer = {
		load : function(e) {
			$("#file-preview-video").attr("src", e.target.result);
		},
		setProperties : function(selectedVideoFile) {
			$("#filename").text(selectedVideoFile.name);
			$("#filetype").text(selectedVideoFile.type);
			$("#filesize").text(Math.round(selectedVideoFile.size/1e+6));
		},
	}

});


function uploadVideo() {

	var storageRef = firebase.storage().ref("/Shows/" + selectedVideoFile.name);
	var uploadTask = storageRef.put(selectedVideoFile);
	var videoProgress = document.getElementById("progress-video");

	uploadTask.on("state_changed", function(snapshot) {

		var videoStatus = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
		console.log("upload is " + videoStatus + "% done");
		videoProgress.style.width = `${videoStatus}%`;
		$("#percentage-video").text(videoStatus.toFixed(0) + "% uploaded");

		switch(snapshot.state) {
			case firebase.storage.TaskState.PAUSED:
				console.log("Upload is paused");
				break;
			case firebase.storage.TaskState.RUNNING:
				console.log("Upload is running");
				break;
		}

	}, function(error) {

	}, function() {
		uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
			console.log("File avaiable at", downloadURL);
			vidRef = downloadURL;
		});

		Swal.fire({
		  title: 'Upload successfull!',
		  text: 'Your file is now available in the app.',
		  icon: 'success',
		  confirmButtonText: 'Finish',
		  allowOutsideClick: false,
  	  	  allowEscapeKey: false
		}).then((result) => { 
		   	uploadToFirestore();
		   	resetForm();  
		});
	});

}


//Uploading to Firebase

var startButton = document.getElementById("start-upload-button");
var backButton = document.getElementById("back-button");
var finishButton = document.getElementById("finish-button");
var title = document.getElementById("vid-title");
var description = document.getElementById("vid-description");
var category = document.getElementById("vid-category");
var closeButton2 = document.getElementById("close-button-2");
var subject = document.getElementById("vid-subject");


startButton.addEventListener("click", function() {
	uploadCoverArt();
	uploadThumbnail();
	uploadVideo();

	backButton.disabled = true;
	startButton.disabled = true;
	closeButton2.disabled = true;	
});

function uploadToFirestore() {

	db.collection("Content").add({
		subject: subject.value,
		category: category.value,
		cover_art: coverRef,
		description: description.value,
		thumbnail: thumbRef,
		title: title.value,
		video_file: vidRef
	})
	.then(function() {
		console.log("Document successfully written!");
	})
	.catch(function(error) {
		console.error("Error writing document: ", error);
	});
}


//Modal events
document.querySelector('form.form-text-areas').addEventListener("submit", function(e) {
	e.preventDefault();
});

//Interchanging of modals
$(document).ready(function() {

	$('#back-button').click(function() {
	    $('#upload-modal-1').modal('show');
	    $('#upload-modal-2').modal('hide');   
	});


});

//Cancel upload
function alertCancel() {
	Swal.fire({
	  title: 'Cancel upload?',
	  text: 'Your file will not be uploaded to the app!',
	  icon: 'warning',
	  showCancelButton: true,
	  confirmButtonText: 'Yes',
	  cancelButtonText: 'No',
	  allowOutsideClick: false,
  	  allowEscapeKey: false
	}).then((result) => {
	  if (result.value) {
	    Swal.fire(
	      'Upload cancelled',
	      'Your file was not uploaded.',
	      'error'
	    )
	    $('#upload-modal-1').modal('hide');
	    $('#upload-modal-2').modal('hide');
	    resetForm();
	  } else if (result.dismiss === Swal.DismissReason.cancel) {
	    
	  }
	});
}

function resetForm() {
	
	//Resets form inputs
	var form = document.getElementById("form-file-details");
	form.reset();

	//Resets file previews 
	previewDefTextThumb.style.display = null;
	previewImgThumb.style.display = null;
	previewDefTextCover.style.display = null;
	previewImgCover.style.display = null;
	$("#file-preview-video").attr("src", "");

	//Resets video properties
	$("#filename").text("");
	$("#filetype").text("");
	$("#filesize").text(""); 

	//Closes modal 2
	$('#upload-modal-2').modal('hide');

	//Enables previously disabled buttons	  
	backButton.disabled = false;
	startButton.disabled = false;
	closeButton2.disabled = false;

	//Resets progress bars
	$('.progress-bar').css('width', '0%').attr('aria-valuenow', 0);

	//Resets video properties
	$("#percentage-cover").text("");
	$("#percentage-thumbnail").text("");
	$("#percentage-video").text("");
}


//Prevents submission of form when there are blank fields
(function() {
  'use strict';
  window.addEventListener('load', function() {
    var forms = document.getElementsByClassName('form-text-areas');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        else {
        	$('#upload-modal-1').modal('hide');
	    	$('#upload-modal-2').modal('show');
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();


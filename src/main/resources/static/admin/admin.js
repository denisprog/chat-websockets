
class MessageEditorApp extends React.Component {
	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
		
	    this.handleShowSuccessModal = this.handleShowSuccessModal.bind(this);
	    this.handleCloseSuccessModal = this.handleCloseSuccessModal.bind(this);
	    
	    this.handleShowErrorModal = this.handleShowErrorModal.bind(this);
	    this.handleCloseErrorModal = this.handleCloseErrorModal.bind(this);
		
		this.state = { messages: [], showSuccessModal: false, showErrorModal: false};
	}
	
	handleShowSuccessModal() {
	    this.setState({ showSuccessModal: true });
	}
	handleCloseSuccessModal() {
		this.setState({ showSuccessModal: false });
	}
	handleShowErrorModal() {
		this.setState({ showErrorModal: true });
	}
	handleCloseErrorModal() {
		this.setState({ showErrorModal: false });
	}

	onUpdate = (val)=> {
	    console.log("Parent onUpdate: "+val);
	    this.setState({messages : val});
	};

	componentDidMount() {
		$.get("getMessages", function(data, status){
        	console.log("Data: " + data + "\nStatus: " + status);
			this.setState({messages: data});
		}.bind(this));
	}
	
	handleSubmit(e) {
		e.preventDefault();
		var messages = this.state.messages;
	    $.ajax({
			type: "POST",
	        url: "updateMessages",
	        dataType: 'json',
	        data: JSON.stringify(messages),
	        contentType: 'application/json',
	        success: function(data) {
	        	this.setState({messages: data});
	        	this.handleShowSuccessModal();
	        }.bind(this),
	        error: function(xhr, status, err) {
	          console.log(status, err.toString());
	          this.handleShowErrorModal();
	        }.bind(this)
	    });
	}

  render() {
	return (
		<div>
			<div class="panel-group" id="accordion">
				{
					this.state.messages.map(function(item){
						return (<MessageComponent message={item} messages={this.state.messages} updateMessages={this.onUpdate} ></MessageComponent>)
					}, this)
				}
			</div>
			<button type="button" class="btn btn-info" onClick={this.handleSubmit}>Save</button>
			
			
	        <ReactBootstrap.Modal show={this.state.showSuccessModal} onHide={this.handleCloseSuccessModal}>
	          <ReactBootstrap.Modal.Header closeButton>
	            <ReactBootstrap.Modal.Title>Result</ReactBootstrap.Modal.Title>
	          </ReactBootstrap.Modal.Header>
	          <ReactBootstrap.Modal.Body>
	            <h4>Success</h4>
	            <p>
	            	All values were saved successfully.
	            </p>
	          </ReactBootstrap.Modal.Body>
	        </ReactBootstrap.Modal>
	        
	        
	        <ReactBootstrap.Modal show={this.state.showErrorModal} onHide={this.handleCloseErrorModal}>
	          <ReactBootstrap.Modal.Header closeButton>
	            <ReactBootstrap.Modal.Title>Result</ReactBootstrap.Modal.Title>
	          </ReactBootstrap.Modal.Header>
	          <ReactBootstrap.Modal.Body>
	            <h4>Error</h4>
	            <p>
	            	Please reload the page and contact administrator.
	            </p>
	          </ReactBootstrap.Modal.Body>
	        </ReactBootstrap.Modal>	
			
		</div>
    )
  }
}

class MessageComponent extends React.Component {

	  constructor(props){
		super(props);
		this.update = this.update.bind(this);		
	  }
	  
	  update (e) {
		  console.log(e.target.value);
		  console.log(this.props.message.id+"-"+this.props.message.messageText+" change to "+e.target.value);
		  this.props.message.messageText=e.target.value;
		  for(var i=0; i<this.props.messages.length; i++){
			  if(this.props.messages[i].id==this.props.message.id){
			    	this.props.messages[i].messageText=e.target.value;
			    	break;
			  }
		  }
		  this.props.updateMessages(this.props.messages);
		  
	  };
	  

	  render() {
	    return (
	  		<div class="panel panel-default">
	    		<div class="panel-heading">
	      			<h4 class="panel-title">
	              		<a data-toggle="collapse" data-parent="#accordion" href={'#' + this.props.message.id}>
	                		{this.props.message.messageName}
	              		</a>
	            	</h4>
	    		</div>
	    		<div id={this.props.message.id} class="panel-collapse collapse">
	      			<div class="panel-body">
						<input type="text" value={this.props.message.messageText} onChange={this.update} />
	      			</div>
	    		</div>
	  		</div>
	    );
	  }
	}

ReactDOM.render(<MessageEditorApp />, document.getElementById("app"));
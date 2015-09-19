/** @jsx React.DOM */
var VotingScreen = React.createClass({
  getInitialState: function() {
    return {enabled: false, selected: null, name: null}
  },
  onParticipantSelected: function(participantId, name) {
    this.setState({enabled: true, selected: participantId, name: name});
  },
  render: function() {
    this.props.buttonDisabled = true;
    this.props.participants = [{id: 1, name: 'Janet', phone: '0800-900-341', la: '3001'}, {id: 2, name: 'Petter', phone: '0800-900-342', la: '3002'}]
    return (
      <div>
        <div className="row row-centered">
          {this.props.participants.map(function(participant, i) {
            return (
              <ParticipantComponent participant={participant} onSelect={this.onParticipantSelected}/>
            );
          }, this)}
        </div>
        <div className="row">
          <div className="col-xs-12">
            <div className="footer text-center" id="footer">
              <VoteComponent buttonText="Vote!" info={this.state}/>
            </div>
          </div>
        </div>
      </div>
    );
  }
});

var ResultScreen = React.createClass({
  getInitialState: function() {
    return {finishDate: new Date('2015-07-10 23:00:00')}
  },
  render: function() {
    return (
      <div>
      <div className="row row-centered">
        <div className="col-xs-5">
          <ParticipantPicture participantId="1"/>
        </div>
        <div className="col-xs-2">
        </div>
        <div className="col-xs-5">
          <ParticipantPicture participantId="2"/>
        </div>
      </div>
        <div className="chart-container">
          <VotesChart />
        </div>
        <CountdownTimer targetTime={this.state.finishDate}/>
      </div>
    );
  }
});

var App = React.createClass({
  render () {
    var Child;
    switch (this.props.route) {
      case 'result':
        Child = ResultScreen;
        break;
      default:
        Child = VotingScreen;
    }

    return (
      <Child/>
    )
  }
});

function render () {
  var route = window.location.hash.substr(1);
  React.render(<App route={route} />, document.getElementById('container'));
}

window.addEventListener('hashchange', render);
render();

/** @jsx React.DOM */

var VoteComponent = React.createClass({
  submitVote: function() {
    React.render(<CaptchaComponent selectedParticipant={this.props.info.selected} selectedName={this.props.info.name}/>, document.getElementById('footer'));
  },
  render: function() {
    return (
        <button type="button" disabled={!this.props.info.enabled} className="btn btn-primary btn-lg" onClick={this.submitVote}>
          {this.props.buttonText}
        </button>
    );
  }
});

var FailMessageComponent = React.createClass({
  render: function() {
    return (
      <div className="alert alert-danger" role="alert">{this.props.message}</div>
    );
  }
});

var SuccessMessageComponent = React.createClass({
  render: function() {
    return (
      <div className="alert alert-success" role="alert">
        <strong>Congrats!</strong> Your vote to fire <strong>{this.props.message}</strong> was sent sucessfully!
      </div>
    );
  }
});

var CaptchaComponent = React.createClass({
  onVoteError: function(error) {
    React.render(<FailMessageComponent message={error.responseText}/>, document.getElementById('message_container'));
  },
  onVoteSuccess: function() {
    window.location.hash = 'result';
    React.render(<SuccessMessageComponent message={this.props.selectedName}/>, document.getElementById('message_container'));
  },
  onCaptchaSuccess: function(captchaKey) {
    React.render(<SpinnerComponent message="Sending your vote..."/>, document.getElementById('footer'));
    var self = this;
    var jsonData = JSON.stringify({ "captcha": captchaKey, "participant": self.props.selectedParticipant });
    var uri = '/vote/';
    $.ajax({
        type        : 'POST',
        url         : uri,
        data        : jsonData,
        contentType : 'application/json',
        success     : self.onVoteSuccess,
        fail        : self.onVoteError,
    });
  },
  componentDidMount: function() {
    var self = this;
    grecaptcha.render('captcha_container', {
      'sitekey' : '6LeQDQkTAAAAAGHTM22yZsYDwN9_RA4gc_G9NmmY',
      'callback': self.onCaptchaSuccess,
    })
  },
  render: function() {
    return (
      <div id="captcha_container" />
    )
  }
});

var ParticipantComponent = React.createClass({
  participantSelect: function() {
    var participantBox = "participant_box_" + this.props.participant.id;
    $('.panel-participant').removeClass('active_vote');
    $('#' + participantBox).addClass('active_vote');
    this.props.onSelect(this.props.participant.id, this.props.participant.name);
  },
  render: function() {
    var participantBox = "participant_box_" + this.props.participant.id;
    return (
        <div className="col-xs-6 col-centered col-fixed">
          <div className="panel-heading">
            <h4><strong>{this.props.participant.name}</strong></h4>
          </div>
          <div id={participantBox} className="panel panel-default panel-participant" onClick={this.participantSelect}>
            <div className="panel-body text-center participant-div">
              <ParticipantPicture participantId={this.props.participant.id} />
            </div>
          </div>
          <div className="panel-footer no-border">
            <ParticipantText participantName={this.props.participant.name} participantPhone={this.props.participant.phone} participantLA={this.props.participant.la}/>
          </div>
        </div>
    );
  }
});

var ParticipantPicture = React.createClass({
  render: function() {
    var classString = "picture-participant  participant" + this.props.participantId;
    return (
      <span className={classString}></span>
    )
  }
});

var ParticipantText = React.createClass({
  render: function() {
    return (
      <p>To fire <strong>{this.props.participantName}</strong> call <strong>{this.props.participantPhone}</strong> or send a SMS to <strong>{this.props.participantLA}</strong></p>
    )
  }
});

var SpinnerComponent = React.createClass({
  render: function() {
    return (
      <div>
          <i className="fa fa-spinner fa-pulse fa-3x fa-fw margin-bottom"></i>
          <p>{this.props.message}</p>
      </div>
    );
  }
});

var VotesChart = React.createClass({
  componentDidMount: function() {
    url = '/vote/percent/byparticipant';
    $.getJSON(url, function(data) {
      var dataSerie = [];
      for (var item in data) {
        dataSerie.push(data[item]);
      }
      new Chartist.Pie('#votes', {
        series: dataSerie
      }, {
        donut: true,
        donutWidth: 50,
        startAngle: 180,
        total: 100,
        showLabel: true
      });
    });
  },
  render: function() {
    return (
      <div className="ct-chart ct-golden-section" id="votes"></div>
    );
  }
});

var CountdownTimer = React.createClass({
  daysLeft: function() {
    var secondsLeft = this.state.secondsLeft / 1000;
    var days = parseInt(secondsLeft / 86400);

    var daysLeftStr = '';
    if (days > 0) {
      if (days == 1) {
        daysLeftStr = days + " DAY ";
      } else {
        daysLeftStr = days + " DAYS ";
      }
    }
    return daysLeftStr;
  },
  hoursLeft: function () {
    var secondsLeft = this.state.secondsLeft / 1000;

    secondsLeft = secondsLeft % 86400;
    var hours = parseInt(secondsLeft / 3600);

    secondsLeft = secondsLeft % 3600;
    var minutes = parseInt(secondsLeft / 60);

    var seconds = parseInt(secondsLeft % 60);

    return hours + ":" + minutes + ":" + seconds;;
  },

  getInitialState: function() {
    return {
      secondsLeft: (this.props.targetTime.getTime() - new Date().getTime())
    };
  },
  tick: function() {
    this.setState({secondsLeft: this.state.secondsLeft - 1000});
    if (this.state.secondsLeft <= 0) {
      clearInterval(this.interval);
    }
  },
  componentDidMount: function() {
    this.interval = setInterval(this.tick, 1000);
  },
  componentWillUnmount: function() {
    clearInterval(this.interval);
  },
  render: function() {
    var daysLeft = this.daysLeft();
    var daysElement;
    if (daysLeft) {
      daysElement = <p><span className="hours-left">{daysLeft}</span></p>
    }
    return (
      <div className="time-left">
        <p>VOTING ENDS IN</p>
        {daysElement}
        <p><span className="hours-left">{this.hoursLeft()}</span></p>
      </div>
    );
  }
});

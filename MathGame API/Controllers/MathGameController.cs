using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MathGame_API;
using System.Security;

namespace MathGame_API.Controllers
{
    [ApiController]
    public class MathGameController : ControllerBase
    {
        private readonly MathGameContext _context;

        public MathGameController(MathGameContext context)
        {
            _context = context;

            if (!_context.Leaderboard.Any())
            {
                for (int i = 0; i < 40; i++) {
                    if (i < 10)
                    {
                         _context.Leaderboard.Add(new Leaderboard
                        { Id=i + 1, Level="Easy", Score=0, Time= 0.00, Username="Fill"});
                    }
                    else if (i >= 10 && i < 20)
                    {
                        _context.Leaderboard.Add(new Leaderboard
                        { Id =i + 1, Level = "Medium", Score = 0, Time = 0.00, Username = "Fill" });
                    }
                    else if (i >= 20 && i < 30)
                    {
                        _context.Leaderboard.Add(new Leaderboard
                        { Id = i + 1, Level = "Hard", Score = 0, Time = 0.00, Username = "Fill" });
                    }
                    else
                    {
                        context.Leaderboard.Add(new Leaderboard
                        { Id = i + 1, Level = "VeryHard", Score = 0, Time = 0.00, Username = "Fill" });
                    }
                }
                _context.SaveChanges();
            }
            if (!_context.LevelSummaries.Any())
            {
                _context.LevelSummaries.Add(new LevelSummary { Level = "Easy", Attempts = 0, TotalScore = 0 });
                _context.LevelSummaries.Add(new LevelSummary { Level = "Medium", Attempts = 0, TotalScore = 0 });
                _context.LevelSummaries.Add(new LevelSummary { Level = "Hard", Attempts = 0, TotalScore = 0 });
                _context.LevelSummaries.Add(new LevelSummary { Level = "VeryHard", Attempts = 0, TotalScore = 0 });
                _context.SaveChanges();
            }
        }

        [HttpGet("/leaderboard/all")]
        public ActionResult<IEnumerable<Leaderboard>> GetLeaderboard()
        {
            var leaderboard = _context.Leaderboard;
            if (leaderboard == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(leaderboard);
            }
        }

        [HttpGet("/leaderboard/{level:alpha}")]
        public ActionResult<IEnumerable<Leaderboard>> GetLeaderboard([FromRoute] string level)
        {
          var leaderboard = _context.Leaderboard.Where(l => l.Level.ToUpper() == level.ToUpper()).OrderByDescending(l => l.Score).ThenBy(l => l.Time);
          if (leaderboard == null)
          {
              return NotFound();
          }
          else {
                return Ok(leaderboard);
          }
        }

        [HttpPut("leaderboard/update/{level:alpha}")]
        public IActionResult PutLeaderboard([FromRoute] string level, [FromBody] Leaderboard newRank)
        {
            var leaderboard = _context.Leaderboard.Where(l => l.Level.ToUpper() == level.ToUpper()).OrderBy(l => l.Score).ThenByDescending(l => l.Time);
            var minScore = leaderboard.Min(r => r.Score);
            var ranks = leaderboard.Where(r => r.Score == minScore).OrderBy(r => r.Time);
            if (ranks == null)
            {
                return BadRequest();
            }
            else
            {
                var maxTime = ranks.Max(r => r.Time);
                var oldRank = ranks.FirstOrDefault(r => r.Time == maxTime);
                oldRank.UpdateRank(newRank);
                _context.SaveChanges();
                return Ok(newRank);
            }
        }

        [HttpGet("/summary/all")]
        public ActionResult<IEnumerable<Leaderboard>> GetSummary()
        {
            var summary = _context.LevelSummaries;
            if (summary == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(summary);
            }
        }

        [HttpPut("summary/update/{level:alpha}")]
        public IActionResult PutSummary([FromRoute] string level, [FromBody] LevelSummary newSummary)
        {
            var summary = _context.LevelSummaries.FirstOrDefault(l => l.Level.ToUpper() == level.ToUpper());
            if (summary == null)
            {
                return BadRequest();
            }
            else
            {
                summary.UpdateSummary(newSummary);
                _context.SaveChanges();
                return Ok(summary);
            }
        }

    }
}

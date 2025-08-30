using System.ComponentModel.DataAnnotations;
using MathGame_API;

namespace MathGame_API
{
    public class Leaderboard
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public string? Level
        {
            get;
            set;
        }

        [Required]
        public string? Username
        {
            get;
            set;
        }

        [Required]
        [Range(0, 20)]
        public int Score
        {
            get;
            set;
        }

        [Required]
        public double Time
        {
            get;
            set;
        }

        public void UpdateRank(Leaderboard l)
        {
            Username = l.Username;
            Score = l.Score;
            Time = l.Time;
        }
    }
}

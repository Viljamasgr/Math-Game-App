using System;
using System.ComponentModel.DataAnnotations;

namespace MathGame_API
{
    public class LevelSummary
    {
        [Required]                          
        [Key]                               
        public string? Level
        {
            get;
            set;
        }

        [Required]
        public int Attempts
        {
            get;
            set;
        }

        public int TotalScore
        {
            get;
            set;
        }

        public void UpdateSummary(LevelSummary s)
        {
            Attempts += 1;
            TotalScore += s.TotalScore;
        }
    }
}
